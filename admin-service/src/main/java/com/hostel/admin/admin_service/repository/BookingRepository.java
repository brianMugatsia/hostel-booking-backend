package com.hostel.admin.admin_service.repository;

import com.hostel.admin.admin_service.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {}
