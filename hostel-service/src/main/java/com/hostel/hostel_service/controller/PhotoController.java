package com.hostel.hostel_service.controller;

import com.hostel.hostel_service.dto.PhotoDTO;
import com.hostel.hostel_service.service.PhotoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/photos")
@CrossOrigin(origins = "http://localhost:3000")
public class PhotoController {

    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    // Only OWNER or ADMIN can upload photos
    @PreAuthorize("hasAnyRole('ADMIN','OWNER')")
    @PostMapping("/hostel/{hostelId}")
    public ResponseEntity<PhotoDTO> uploadPhoto(@PathVariable Long hostelId,
            @RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(photoService.uploadPhoto(hostelId, file));
    }

    // Everyone can view hostel photos
    @GetMapping("/hostel/{hostelId}")
    public ResponseEntity<List<PhotoDTO>> getPhotosByHostel(@PathVariable Long hostelId) {
        return ResponseEntity.ok(photoService.getPhotosByHostel(hostelId));
    }
}
