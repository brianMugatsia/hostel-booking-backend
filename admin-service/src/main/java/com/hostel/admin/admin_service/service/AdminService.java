package com.hostel.admin.admin_service.service;

import com.hostel.admin.admin_service.model.*;
import java.util.List;

public interface AdminService {
    List<Hostel> getHostels();
    Hostel addHostel(Hostel hostel);

    List<Room> getRooms();
    Room addRoom(Room room);

    List<Booking> getBookings();
    List<Payment> getPayments();
}
