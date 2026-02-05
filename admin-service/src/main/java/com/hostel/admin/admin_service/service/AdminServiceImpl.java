package com.hostel.admin.admin_service.service;

import com.hostel.admin.admin_service.model.*;
import com.hostel.admin.admin_service.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final HostelRepository hostelRepository;
    private final RoomRepository roomRepository;
    private final RestTemplate restTemplate;

    public AdminServiceImpl(HostelRepository hostelRepository,
                            RoomRepository roomRepository,
                            RestTemplate restTemplate) {
        this.hostelRepository = hostelRepository;
        this.roomRepository = roomRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Hostel> getHostels() { return hostelRepository.findAll(); }

    @Override
    public Hostel addHostel(Hostel hostel) { return hostelRepository.save(hostel); }

    @Override
    public List<Room> getRooms() { return roomRepository.findAll(); }

    @Override
    public Room addRoom(Room room) { return roomRepository.save(room); }

    @Override
    public List<Booking> getBookings() {
        Booking[] bookings = restTemplate.getForObject(
                "http://BOOKING-SERVICE/bookings", Booking[].class);
        return Arrays.asList(bookings);
    }

    @Override
    public List<Payment> getPayments() {
        Payment[] payments = restTemplate.getForObject(
                "http://PAYMENT-SERVICE/payments", Payment[].class);
        return Arrays.asList(payments);
    }
}
