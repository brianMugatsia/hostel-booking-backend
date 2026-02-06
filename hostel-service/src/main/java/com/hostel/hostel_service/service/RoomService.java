package com.hostel.hostel_service.service;

import com.hostel.hostel_service.dto.RoomDTO;
import com.hostel.hostel_service.entity.Hostel;
import com.hostel.hostel_service.entity.Room;
import com.hostel.hostel_service.exception.RoomNotFoundException;
import com.hostel.hostel_service.repository.HostelRepository;
import com.hostel.hostel_service.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final HostelRepository hostelRepository;

    public RoomService(RoomRepository roomRepository, HostelRepository hostelRepository) {
        this.roomRepository = roomRepository;
        this.hostelRepository = hostelRepository;
    }

    // Get all rooms
    public List<RoomDTO> getAllRooms() {
        return roomRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Get rooms by hostel
    public List<RoomDTO> getRoomsByHostel(Long hostelId) {
        return roomRepository.findByHostelId(hostelId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Get room by ID
    public RoomDTO getRoomById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException(id));
        return mapToDTO(room);
    }

    // Create room
    public RoomDTO createRoom(RoomDTO dto) {
        Hostel hostel = hostelRepository.findById(dto.getHostelId())
                .orElseThrow(() -> new RuntimeException("Hostel not found"));

        Room room = new Room();
        room.setType(dto.getType());
        room.setPrice(dto.getPrice());
        room.setCapacity(dto.getCapacity());
        room.setAvailable(dto.getAvailable());
        room.setHostel(hostel);

        Room saved = roomRepository.save(room);
        return mapToDTO(saved);
    }

    // Update room
    public RoomDTO updateRoom(Long id, RoomDTO dto) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException(id));

        room.setType(dto.getType());
        room.setPrice(dto.getPrice());
        room.setCapacity(dto.getCapacity());
        room.setAvailable(dto.getAvailable());

        Hostel hostel = hostelRepository.findById(dto.getHostelId())
                .orElseThrow(() -> new RuntimeException("Hostel not found"));
        room.setHostel(hostel);

        Room updated = roomRepository.save(room);
        return mapToDTO(updated);
    }

    // Delete room
    public void deleteRoom(Long id) {
        if (!roomRepository.existsById(id)) {
            throw new RoomNotFoundException(id);
        }
        roomRepository.deleteById(id);
    }

    // Mapper
    private RoomDTO mapToDTO(Room room) {
        RoomDTO dto = new RoomDTO();
        dto.setId(room.getId());
        dto.setType(room.getType());
        dto.setPrice(room.getPrice());
        dto.setCapacity(room.getCapacity());
        dto.setAvailable(room.getAvailable());
        dto.setHostelId(room.getHostel().getId());
        return dto;
    }
}
