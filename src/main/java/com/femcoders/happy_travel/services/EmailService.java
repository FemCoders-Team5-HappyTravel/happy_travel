package com.femcoders.happy_travel.services;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    @PostConstruct
    public void printMailConfig() {
        System.out.println("MAIL DEBUG:");
        System.out.println("spring.mail.username = " + System.getenv("EMAIL"));
        System.out.println("spring.mail.password = " + System.getenv("GMAIL_PASSWORD"));
    }


    public void sendWelcomeEmail(String toEmail, String username) {


        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("¡Bienvenido a HappyTravel!");
        message.setText("Hola " + username + ",\n\n¡Gracias por registrarte en HappyTravel! 🧳\n\n¡Esperamos que disfrutes tu viaje!");
        message.setFrom("vitaliaflash@gmail.com");

        try {
            mailSender.send(message);
            System.out.println("Email sent to: " + toEmail);
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }

    }

}