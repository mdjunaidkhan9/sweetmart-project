package com.project.cart.feign;

import com.project.cart.model.ProductEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PRODUCTSERVICE")
public interface ProductFeignClient {
	  @GetMapping("/productbyname/{name}")
	    ProductEntity getProductByName(@PathVariable("name") String name);
}
