package com.project.category.feign;


import com.project.category.entity.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "PRODUCTSERVICE")
public interface ProductClient {

    @GetMapping("/productbycategory/{category}")
    List<ProductDTO> getProductsByCategory(@PathVariable("category") String categoryName);
}
