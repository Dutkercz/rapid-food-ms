package com.db.ar.feign.product;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductFeignFallback implements FallbackFactory<ProductFeignClient> {

    @Override
    public ProductFeignClient create(Throwable cause) {
        return new ProductFeignClient() {
            @Override
            public ResponseEntity<ProductFeignDto> getById(Long productId) {
                throw new EntityNotFoundException("Product not found or unavailable at the moment.");
            }
        };
    }
}
