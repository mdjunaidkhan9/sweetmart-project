package com.project.cart.feign;

import com.project.cart.model.CustomerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CUSTOMERSERVICE")
public interface CustomerFeignClient {
    @GetMapping("/customers/{username}")
    CustomerDTO getByUsername(@PathVariable("username") String username);
}
