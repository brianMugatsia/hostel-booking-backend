package com.hostel.notification_service.controller;

import com.hostel.notification_service.entity.Notification;
import com.hostel.notification_service.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "http://localhost:3000")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/email")
    public ResponseEntity<String> sendEmail(@RequestBody Notification notification) {
        notificationService.sendEmail(notification);
        return ResponseEntity.ok("Email sent successfully");
    }

    @PostMapping("/sms")
    public ResponseEntity<String> sendSMS(@RequestBody Notification notification) {
        notificationService.sendSMS(notification);
        return ResponseEntity.ok("SMS sent successfully");
    }
}
