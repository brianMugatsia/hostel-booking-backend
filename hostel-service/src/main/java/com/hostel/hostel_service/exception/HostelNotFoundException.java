package com.hostel.hostel_service.exception;

public class HostelNotFoundException extends RuntimeException {
    public HostelNotFoundException(Long id) {
        super("Hostel with ID " + id + " not found");
    }
}
