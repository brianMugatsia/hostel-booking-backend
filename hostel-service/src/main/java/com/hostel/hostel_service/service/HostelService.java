package com.hostel.hostel_service.service;

import com.hostel.hostel_service.entity.Hostel;
import com.hostel.hostel_service.repository.HostelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HostelService {

    private final HostelRepository hostelRepository;

    public HostelService(HostelRepository hostelRepository) {
        this.hostelRepository = hostelRepository;
    }

    public List<Hostel> getAllHostels() {
        return hostelRepository.findAll();
    }

    public Hostel getHostelById(Long id) {
        return hostelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hostel not found"));
    }

    public Hostel createHostel(Hostel hostel) {
        return hostelRepository.save(hostel);
    }

    public Hostel updateHostel(Long id, Hostel hostel) {
        Hostel existing = getHostelById(id);
        existing.setName(hostel.getName());
        existing.setLocation(hostel.getLocation());
        existing.setDescription(hostel.getDescription());
        return hostelRepository.save(existing);
    }

    public void deleteHostel(Long id) {
        hostelRepository.deleteById(id);
    }
}
