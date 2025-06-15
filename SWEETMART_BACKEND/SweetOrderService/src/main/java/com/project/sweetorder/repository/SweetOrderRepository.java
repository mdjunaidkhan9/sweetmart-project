package com.project.sweetorder.repository;

import com.project.sweetorder.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SweetOrderRepository extends JpaRepository<SweetOrder, Long> {
    List<SweetOrder> findByUsername(String username);
}
