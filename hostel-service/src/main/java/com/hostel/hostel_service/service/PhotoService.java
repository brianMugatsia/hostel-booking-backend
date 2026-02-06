package com.hostel.hostel_service.service;

import com.hostel.hostel_service.dto.PhotoDTO;
import com.hostel.hostel_service.entity.Hostel;
import com.hostel.hostel_service.entity.Photo;
import com.hostel.hostel_service.exception.HostelNotFoundException;
import com.hostel.hostel_service.repository.HostelRepository;
import com.hostel.hostel_service.repository.PhotoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PhotoService {

    private final S3Client s3Client;
    private final String bucketName = "your-bucket-name";
    private final PhotoRepository photoRepository;
    private final HostelRepository hostelRepository;

    public PhotoService(S3Client s3Client, PhotoRepository photoRepository, HostelRepository hostelRepository) {
        this.s3Client = s3Client;
        this.photoRepository = photoRepository;
        this.hostelRepository = hostelRepository;
    }

    public PhotoDTO uploadPhoto(Long hostelId, MultipartFile file) throws IOException {
        Hostel hostel = hostelRepository.findById(hostelId)
                .orElseThrow(() -> new HostelNotFoundException(hostelId));

        String key = UUID.randomUUID() + "_" + file.getOriginalFilename();

        // Build request
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(file.getContentType())
                .build();

        // Upload file
        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        // Construct URL manually (v2 doesn’t have getUrl)
        String url = "https://" + bucketName + ".s3." + s3Client.serviceClientConfiguration().region().id()
                + ".amazonaws.com/" + key;

        Photo photo = new Photo();
        photo.setFilename(file.getOriginalFilename());
        photo.setUrl(url);
        photo.setUploadedAt(LocalDateTime.now());
        photo.setHostel(hostel);

        Photo saved = photoRepository.save(photo);

        return mapToDTO(saved);
    }

    public List<PhotoDTO> getPhotosByHostel(Long hostelId) {
        return photoRepository.findByHostelId(hostelId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private PhotoDTO mapToDTO(Photo photo) {
        PhotoDTO dto = new PhotoDTO();
        dto.setId(photo.getId());
        dto.setFilename(photo.getFilename());
        dto.setUrl(photo.getUrl());
        dto.setUploadedAt(photo.getUploadedAt());
        dto.setHostelId(photo.getHostel().getId());
        return dto;
    }
}
