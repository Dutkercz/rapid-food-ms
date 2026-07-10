package com.db.ar.feign;

import com.db.ar.feign.dtos.ProductFeignDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:8083/api/products", name = "order.products")
public interface ProductFeignClient {

    @GetMapping("/{productId}")
    ResponseEntity<ProductFeignDto> getById(@PathVariable Long productId);
}
