package com.hostel.hostel_service.controller;

import com.hostel.hostel_service.dto.HostelDTO;
import com.hostel.hostel_service.service.HostelService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hostels")
@CrossOrigin(origins = "http://localhost:3000")
public class HostelController {

    private final HostelService hostelService;

    public HostelController(HostelService hostelService) {
        this.hostelService = hostelService;
    }

    // Everyone can view hostels
    @GetMapping
    public ResponseEntity<List<HostelDTO>> getAllHostels() {
        return ResponseEntity.ok(hostelService.getAllHostels());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HostelDTO> getHostelById(@PathVariable Long id) {
        return ResponseEntity.ok(hostelService.getHostelById(id));
    }

    // Only ADMIN or OWNER can add hostels
    @PreAuthorize("hasAnyRole('ADMIN','OWNER')")
    @PostMapping
    public ResponseEntity<HostelDTO> createHostel(@Valid @RequestBody HostelDTO hostelDTO) {
        HostelDTO created = hostelService.createHostel(hostelDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Only ADMIN or OWNER can update hostels
    @PreAuthorize("hasAnyRole('ADMIN','OWNER')")
    @PutMapping("/{id}")
    public ResponseEntity<HostelDTO> updateHostel(@PathVariable Long id, @Valid @RequestBody HostelDTO hostelDTO) {
        return ResponseEntity.ok(hostelService.updateHostel(id, hostelDTO));
    }

    // Only ADMIN can delete hostels
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHostel(@PathVariable Long id) {
        hostelService.deleteHostel(id);
        return ResponseEntity.noContent().build();
    }
}
