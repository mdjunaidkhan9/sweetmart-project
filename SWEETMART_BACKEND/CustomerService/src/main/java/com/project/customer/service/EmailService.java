package com.project.customer.service;

import org.springframework.stereotype.Service;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.util.Properties;
@Service // Marks this as a Spring service
public class EmailService {

    private final String username = "mohammedjunaidkhan990@gmail.com";
    private final String password = "buum oikk lyca qiox"; // ⚠️ Use environment variables in production

    public void sendEmail(String recipient, String subject, String userName, String userRole) {
        Properties props = new Properties(); // Configures Gmail SMTP
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password); // Gmail login
            }
        });

        try {
            Message message = new MimeMessage(session); // Constructs email
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText( // Body of the email
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
            Transport.send(message); // Sends the email
            System.out.println("Email sent successfully to " + recipient);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Failed to send email: " + e.getMessage());
        }
    }
}
