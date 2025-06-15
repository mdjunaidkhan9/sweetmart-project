//package com.project.auth.service;
//
//import com.project.auth.model.User;
//import com.project.auth.repo.UserRepository;
////import com.project.auth.service.JwtUtil;
//import com.project.auth.exception.AuthenticationException;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Random;
//
//@Service
//public class AuthService {
//
//    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
//
//    private final UserRepository repo;
//    private final PasswordEncoder encoder;
//    private final JwtUtil jwtUtil;
//    private final EmailService emailService;
//
//    // ✅ Constructor Injection
//    public AuthService(UserRepository repo, PasswordEncoder encoder, JwtUtil jwtUtil, EmailService emailService) {
//        this.repo = repo;
//        this.encoder = encoder;
//        this.jwtUtil = jwtUtil;
//        this.emailService = emailService;
//    }
//
//    public String register(User user) {
//        if (repo.findByUsername(user.getUsername()).isPresent()) {
//            return "Username taken";
//        }
//
//        user.setPassword(encoder.encode(user.getPassword()));
//        repo.save(user);
//
//        try {
//            emailService.sendEmail(
//                    user.getUserEmail(),
//                    "Registration Successful",
//                    "Welcome, " + user.getUsername() + "! Your account is now active.",
//                    user.getRole()
//            );
//        } catch (Exception e) {
//            logger.error("Email sending failed: {}", e.getMessage());
//        }
//
//        return "User registered successfully";
//    }
//
//    public String login(String username, String password) {
//        User user = repo.findByUsername(username)
//                .orElseThrow(() -> new AuthenticationException("User not found"));
//
//        if (encoder.matches(password, user.getPassword())) {
//            return jwtUtil.generateToken(user.getUsername(), user.getRole());
//        } else {
//            throw new AuthenticationException("Invalid credentials");
//        }
//    }
//
//    public List<User> getAllUsers() {
//        return repo.findAll();
//    }
//
//    public List<User> getUsersByRole(String role) {
//        return repo.findByRoleIgnoreCase(role);
//    }
//    public String generateOtpAndSend(User user) {
//        String otp = String.format("%06d", new Random().nextInt(999999));
//        
//        User newUser = new User();
//        newUser.setUsername(user.getUsername());
//        newUser.setUserEmail(user.getUserEmail());
//        newUser.setPassword(encoder.encode(user.getPassword()));
//        newUser.setRole(user.getRole());
//        newUser.setOtp(otp);
//        newUser.setVerified(false);
//
//        repo.save(newUser);
//
//        emailService.sendOtpEmail(user.getUserEmail(), otp);
//        return "OTP sent to email";
//    }
//
//    public boolean verifyOtp(String username, String otp) {
//        User user = repo.findByUsername(username).orElse(null);
//        if (user != null && user.getOtp().equals(otp)) {
//            user.setVerified(true);
//            user.setOtp(null); // clear OTP after successful verification
//            repo.save(user);
//            return true;
//        }
//        return false;
//    }
//
//}
package com.project.auth.service;

import com.project.auth.exception.AuthenticationException;
import com.project.auth.model.User;
import com.project.auth.repo.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthService {
    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final EmailService emailService;
    private final JwtUtil jwtUtil;
    public AuthService(UserRepository repo, PasswordEncoder encoder, EmailService emailService, JwtUtil jwtUtil) {
        this.repo = repo;
        this.encoder = encoder;
        this.emailService = emailService;
        this.jwtUtil=jwtUtil;
    }

    public String sendOtp(User user) {
        if (repo.findByUsername(user.getUsername()).isPresent()) {
            return "Username already exists";
        }
        if (repo.findByUserEmail(user.getUserEmail()).isPresent()) {
            return "Email already registered";
        }
        String otp = String.format("%06d", new Random().nextInt(1000000));
        System.out.println("Generated OTP: " + otp); // Debug log
        user.setOtp(otp);
        user.setOtpVerified(false);
        user.setPassword(encoder.encode(user.getPassword()));
        repo.save(user);
        emailService.sendOtpEmail(user.getUserEmail(), otp);
        return "OTP sent to email";
    }

    public String verifyOtp(String username, String otp) {
        Optional<User> userOpt = repo.findByUsername(username);
        if (userOpt.isEmpty()) return "User not found";
        User user = userOpt.get();
        if (user.isOtpVerified()) return "OTP already verified";
        if (user.getOtp().equals(otp)) {
            user.setOtpVerified(true);
            repo.save(user);
            emailService.sendEmail(user.getUserEmail(), "Welcome to Online Sweet Mart!", user.getUsername(), user.getRole());
            return "OTP verified successfully";
        }
        return "Invalid OTP";
    }

  public String login(String username, String password) {
      User user = repo.findByUsername(username)
              .orElseThrow();

      if (encoder.matches(password, user.getPassword())) {
          return jwtUtil.generateToken(user.getUsername(), user.getRole());
      } else {
          throw new AuthenticationException("Invalid credentials");
      }
  }
}

