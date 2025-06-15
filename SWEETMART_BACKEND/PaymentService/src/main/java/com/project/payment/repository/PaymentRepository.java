package com.project.payment.repository;

import com.project.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.*;
public interface PaymentRepository extends JpaRepository<Payment, String> {
    Optional<Payment> findTopByUsernameOrderByPaymentIdDesc(String username);
    List<Payment> findByUsernameOrderByPaymentIdDesc(String username);
}
