package com.db.ar.feign.product;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:8083/api/products", name = "order.products", fallbackFactory =  ProductFeignClientFallback.class)
public interface ProductFeignClient {

    @GetMapping("/{productId}")
    ResponseEntity<ProductFeignDto> getById(@PathVariable Long productId);
}
