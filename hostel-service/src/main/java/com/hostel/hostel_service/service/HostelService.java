package com.hostel.hostel_service.service;

import com.hostel.hostel_service.dto.HostelDTO;
import com.hostel.hostel_service.entity.Hostel;
import com.hostel.hostel_service.exception.HostelNotFoundException;
import com.hostel.hostel_service.repository.HostelRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class HostelService {

    private final HostelRepository hostelRepository;

    public HostelService(HostelRepository hostelRepository) {
        this.hostelRepository = hostelRepository;
    }

    public HostelDTO createHostel(HostelDTO dto) {
        Hostel hostel = new Hostel();
        hostel.setName(dto.getName());
        hostel.setLocation(dto.getLocation());
        hostel.setDescription(dto.getDescription());
        hostel.setRooms(new ArrayList<>());

        Hostel saved = hostelRepository.save(hostel);

        return mapToDTO(saved);
    }

    public HostelDTO getHostelById(Long id) {
        Hostel hostel = hostelRepository.findById(id)
                .orElseThrow(() -> new HostelNotFoundException(id));
        return mapToDTO(hostel);
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

    private HostelDTO mapToDTO(Hostel hostel) {
        HostelDTO dto = new HostelDTO();
        dto.setId(hostel.getId());
        dto.setName(hostel.getName());
        dto.setLocation(hostel.getLocation());
        dto.setDescription(hostel.getDescription());
        dto.setTotalRooms(hostel.getRooms().size());
        return dto;
    }
}
