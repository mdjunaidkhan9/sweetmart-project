package com.project.cart.feign;

import com.project.cart.model.SweetItemDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "SWEETITEMSERVICE")
public interface SweetItemFeignClient {
    @GetMapping("/sweetitems/{id}")
    SweetItemDTO getSweetItemById(@PathVariable("id") Long id);
}
