package com.hostel.admin.admin_service.repository;

import com.hostel.admin.admin_service.model.Hostel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HostelRepository extends JpaRepository<Hostel, Long> {}
