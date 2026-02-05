package com.hostel.hostel_service.exception;

public class RoomNotFoundException extends RuntimeException {
    public RoomNotFoundException(Long id) {
        super("Room with ID " + id + " not found");
    }
}
