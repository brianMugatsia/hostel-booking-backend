package com.hostel.hostel_service.controller;

import com.hostel.hostel_service.dto.RoomDTO;
import com.hostel.hostel_service.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@CrossOrigin(origins = "http://localhost:3000")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    // Everyone can view rooms
    @GetMapping
    public ResponseEntity<List<RoomDTO>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @GetMapping("/hostel/{hostelId}")
    public ResponseEntity<List<RoomDTO>> getRoomsByHostel(@PathVariable Long hostelId) {
        return ResponseEntity.ok(roomService.getRoomsByHostel(hostelId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    // Only ADMIN or OWNER can add rooms
    @PreAuthorize("hasAnyRole('ADMIN','OWNER')")
    @PostMapping
    public ResponseEntity<RoomDTO> createRoom(@Valid @RequestBody RoomDTO roomDTO) {
        RoomDTO created = roomService.createRoom(roomDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Only ADMIN or OWNER can update rooms
    @PreAuthorize("hasAnyRole('ADMIN','OWNER')")
    @PutMapping("/{id}")
    public ResponseEntity<RoomDTO> updateRoom(@PathVariable Long id, @Valid @RequestBody RoomDTO roomDTO) {
        return ResponseEntity.ok(roomService.updateRoom(id, roomDTO));
    }

    // Only ADMIN can delete rooms
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }
}
