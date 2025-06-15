package com.project.product.feign;

import com.project.product.model.CategoryEntityDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "CATEGORYSERVICE")
public interface CategoryClient {

	@GetMapping("/hellotoproduct")
	String hellotoproduct();
    @GetMapping("/allcategory")
    List<CategoryEntityDTO> getAllCategories();
}
