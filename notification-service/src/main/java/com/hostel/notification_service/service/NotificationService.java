package com.hostel.notification_service.service;

import com.hostel.notification_service.entity.Notification;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final JavaMailSender mailSender;

    public NotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(Notification notification) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(notification.getTo());
        message.setSubject("Hostel Booking Notification");
        message.setText(notification.getMessage());
        mailSender.send(message);
    }

    // For SMS, you can integrate Twilio or any SMS API
    public void sendSMS(Notification notification) {
        // Example placeholder:
        System.out.println("SMS to " + notification.getTo() + ": " + notification.getMessage());
    }
}
