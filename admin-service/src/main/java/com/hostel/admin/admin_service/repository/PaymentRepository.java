package com.hostel.admin.admin_service.repository;

import com.hostel.admin.admin_service.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {}
