package com.hostel.hostel_service.repository;

import com.hostel.hostel_service.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findByHostelId(Long hostelId);
}
