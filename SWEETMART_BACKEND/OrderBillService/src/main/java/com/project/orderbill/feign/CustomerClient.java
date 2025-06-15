package com.project.orderbill.feign;

import com.project.orderbill.dto.CustomerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CUSTOMERSERVICE")
public interface CustomerClient {
    @GetMapping("/user/get/{username}")
    CustomerDTO getCustomer(@PathVariable String username);
}
