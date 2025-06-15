package com.project.orderbill.service;

import com.project.orderbill.dto.CustomerDTO;
import com.project.orderbill.dto.SweetOrderDTO;
import com.project.orderbill.entity.Bill;
import com.project.orderbill.feign.CustomerClient;
import com.project.orderbill.feign.SweetOrderClient;
import com.project.orderbill.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillService {

    @Autowired
    private SweetOrderClient sweetOrderClient;
    @Autowired
    private CustomerClient customerClient;
    @Autowired
    private BillRepository billRepository;

    public ResponseEntity<Bill> createBill(String username) {
        if (username == null || username.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        List<SweetOrderDTO> orders = sweetOrderClient.getOrders(username);
        if (orders == null || orders.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        SweetOrderDTO latestOrder = orders.get(orders.size() - 1);
        CustomerDTO customer = customerClient.getCustomer(username);

        Bill bill = new Bill();
        bill.setSweetOrderId(latestOrder.getId());
        bill.setUsername(username);
        bill.setName(customer.getName());
        bill.setShippingAddress(customer.getShippingAddress());
        bill.setTotalAmount(latestOrder.getTotalAmount());
        bill.setCreatedDate(java.time.LocalDateTime.now());

        Bill saved = billRepository.save(bill);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    public ResponseEntity<Bill> getLatestBill(String username) {
        if (username == null || username.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Bill bill = billRepository.findTopByUsernameOrderByCreatedDateDesc(username);
        if (bill == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        return ResponseEntity.ok(bill);
    }

    public ResponseEntity<List<Bill>> getAllBills() {
        List<Bill> bills = billRepository.findAll();
        if (bills == null || bills.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(bills);
        }
        return ResponseEntity.ok(bills);
    }
    public ResponseEntity<List<Bill>> getAllBillsByUser(String username) {
        if (username == null || username.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        List<Bill> bills = billRepository.findByUsernameOrderByCreatedDateDesc(username);
        if (bills == null || bills.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(bills);
        }
        return ResponseEntity.ok(bills);
    }

}
