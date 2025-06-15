package com.project.orderbill.repository;

import com.project.orderbill.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill, Long> {
    Bill findTopByUsernameOrderByCreatedDateDesc(String username);
    List<Bill> findByUsernameOrderByCreatedDateDesc(String username);
}
