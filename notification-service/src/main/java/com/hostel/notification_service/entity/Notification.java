package com.hostel.notification_service.entity;

public class Notification {
    private String to;
    private String type; // EMAIL or SMS
    private String message;

    // Constructors, getters, setters
    public Notification() {}
    public Notification(String to, String type, String message) {
        this.to = to;
        this.type = type;
        this.message = message;
    }
    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
