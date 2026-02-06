package com.hostel.hostel_service.service;

import com.hostel.hostel_service.dto.HostelDTO;
import com.hostel.hostel_service.entity.Hostel;
import com.hostel.hostel_service.entity.Photo;
import com.hostel.hostel_service.exception.HostelNotFoundException;
import com.hostel.hostel_service.repository.HostelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HostelService {

    private final HostelRepository hostelRepository;

    public HostelService(HostelRepository hostelRepository) {
        this.hostelRepository = hostelRepository;
    }

    public List<HostelDTO> getAllHostels() {
        return hostelRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public HostelDTO getHostelById(Long id) {
        Hostel hostel = hostelRepository.findById(id)
                .orElseThrow(() -> new HostelNotFoundException(id));
        return mapToDTO(hostel);
    }

    public HostelDTO createHostel(HostelDTO dto) {
        Hostel hostel = new Hostel();
        hostel.setName(dto.getName());
        hostel.setLocation(dto.getLocation());
        hostel.setDescription(dto.getDescription());

        Hostel saved = hostelRepository.save(hostel);
        return mapToDTO(saved);
    }

    public HostelDTO updateHostel(Long id, HostelDTO dto) {
        Hostel hostel = hostelRepository.findById(id)
                .orElseThrow(() -> new HostelNotFoundException(id));

        hostel.setName(dto.getName());
        hostel.setLocation(dto.getLocation());
        hostel.setDescription(dto.getDescription());

        Hostel updated = hostelRepository.save(hostel);
        return mapToDTO(updated);
    }

    public void deleteHostel(Long id) {
        if (!hostelRepository.existsById(id)) {
            throw new HostelNotFoundException(id);
        }
        hostelRepository.deleteById(id);
    }

    // Mapper now includes photo URLs
    private HostelDTO mapToDTO(Hostel hostel) {
        HostelDTO dto = new HostelDTO();
        dto.setId(hostel.getId());
        dto.setName(hostel.getName());
        dto.setLocation(hostel.getLocation());
        dto.setDescription(hostel.getDescription());
        dto.setTotalRooms(hostel.getRooms() != null ? hostel.getRooms().size() : 0);

        if (hostel.getPhotos() != null) {
            dto.setPhotoUrls(
                    hostel.getPhotos().stream()
                            .map(Photo::getUrl)
                            .collect(Collectors.toList()));
        }

        return dto;
    }
}
