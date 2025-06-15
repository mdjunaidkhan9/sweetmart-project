package com.project.auth.service;

import org.springframework.stereotype.Service;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

@Service
public class EmailService {

    private final String username = "your user name";
    private final String password = "app password"; // ⚠️ Use env vars in production

    public void sendEmail(String recipient, String subject, String userName, String userRole) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(
                "Dear " + userName + ",\n\n"
                + "We are thrilled to welcome you to our platform. Your account has been successfully created.\n\n"
                + "For security reasons, please ensure that you keep your login credentials private. "
                + "If you ever need assistance, our support team is available to help.\n\n"
                + "Your Account Details:\n"
                + "- Username: " + userName + "\n"
                + "- Email: " + recipient + "\n"
                + "- Role: " + userRole + "\n\n"
                + "Secure Your Account:\n"
                + "We recommend setting up two-factor authentication (if available) and keeping your password updated periodically.\n\n"
                + "Thank you for joining us. We look forward to serving you.\n\n"
                + "Best regards,\n"
                + "Online Sweet Mart Team\n"
                + "mohammedjunaidkhan990@gmail.com "
            );
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendOtpEmail(String recipient, String otp) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject("OTP for Registration");
            message.setText("Your OTP for registration is: " + otp + "\n\nThis OTP is valid for 5 minutes.");
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
