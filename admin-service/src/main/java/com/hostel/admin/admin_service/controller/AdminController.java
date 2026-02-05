package com.hostel.admin.admin_service.controller;

import com.hostel.admin.admin_service.model.*;
import com.hostel.admin.admin_service.service.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/hostels")
    public List<Hostel> getHostels() {
        return adminService.getHostels();
    }

    @PostMapping("/hostels")
    public Hostel addHostel(@RequestBody Hostel hostel) {
        return adminService.addHostel(hostel);
    }

    @GetMapping("/rooms")
    public List<Room> getRooms() {
        return adminService.getRooms();
    }

    @PostMapping("/rooms")
    public Room addRoom(@RequestBody Room room) {
        return adminService.addRoom(room);
    }

    @GetMapping("/bookings")
    public List<Booking> getBookings() {
        return adminService.getBookings();
    }

    @GetMapping("/payments")
    public List<Payment> getPayments() {
        return adminService.getPayments();
    }
}
