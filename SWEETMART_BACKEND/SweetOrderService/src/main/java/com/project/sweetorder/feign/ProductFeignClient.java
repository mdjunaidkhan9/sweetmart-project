package com.project.sweetorder.feign;

import com.project.sweetorder.model.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PRODUCTSERVICE")
public interface ProductFeignClient {

    @GetMapping("/{id}")
    ProductDTO getProductById(@PathVariable("id") Long id);
}
