package com.db.ar.feign.product;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductFeignClientFallback implements FallbackFactory<ProductFeignClient> {

    @Override
    public ProductFeignClient create(Throwable cause) {
        return new ProductFeignClient() {
            @Override
            public ResponseEntity<ProductFeignDto> getById(Long productId) {
                var productFallback = new ProductFeignDto(
                        productId, "indisponível", "indisponível",
                        null, null, null, null, null);
                return ResponseEntity.ok(productFallback);
            }
        };
    }
}
