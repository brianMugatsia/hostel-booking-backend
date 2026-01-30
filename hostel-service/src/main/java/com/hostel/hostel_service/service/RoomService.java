package com.hostel.hostel_service.service;

import com.hostel.hostel_service.entity.Room;
import com.hostel.hostel_service.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public List<Room> getRoomsByHostel(Long hostelId) {
        return roomRepository.findByHostelId(hostelId);
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
    }

    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    public Room updateRoom(Long id, Room room) {
        Room existing = getRoomById(id);
        existing.setType(room.getType());
        existing.setPrice(room.getPrice());
        existing.setCapacity(room.getCapacity());
        existing.setAvailable(room.getAvailable());
        existing.setHostel(room.getHostel());
        return roomRepository.save(existing);
    }

    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }
}
