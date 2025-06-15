package com.project.sweetorder.feign;

import com.project.sweetorder.model.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.*;
@FeignClient(name = "CARTSERVICE")
public interface CartFeignClient { 
	@GetMapping("/user/{username}")
    List<CartItemDTO> getCartByUsername(@PathVariable("username") String username);

    @DeleteMapping("/user/clear/{username}")
    void clearCart(@PathVariable("username") String username);
}
