package com.sdc.tradeo.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendVerificationOtpEmail(String email, String otp) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");

        String subject = "Verify Your OTP - TradeO";

        String htmlContent = "<div style='font-family: Arial, sans-serif; padding: 15px;'>" +
                "<h2 style='color: #007bff;'>🔐 Two-Step Verification</h2>" +
                "<p>Dear User,</p>" +
                "<p>Your OTP for verification is: <strong style='font-size: 18px;'>" + otp + "</strong></p>" +
                "<p>This OTP is valid for <strong>5 minutes</strong>. Do not share this code with anyone.</p>" +
                "<p>If you didn't request this, please ignore this email.</p>" +
                "<br><p>Best Regards,<br><strong>TradeO Team</strong></p>" +
                "</div>";

        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(htmlContent, true); // Enable HTML format
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setFrom("no-reply@tradeo.com"); // Ensure this email is configured in application.properties

        try {
            javaMailSender.send(mimeMessage);
            System.out.println("OTP Email Sent Successfully to " + email);
        } catch (MailException e) {
            throw new MailSendException("Failed to send OTP email: " + e.getMessage());
        }
    }
}
