package com.db.ar.feign.product;

import com.db.ar.feign.user.UserFeignClientFallback;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ProductFeignClientFallback implements FallbackFactory<ProductFeignClient> {

    @Override
    public ProductFeignClient create(Throwable cause) {
        return new ProductFeignClient() {
            @Override
            public ResponseEntity<ProductFeignDto> getById(Long productId) {
                var productResponse = new ProductFeignDto(productId, "indisponível",
                  "indisponível", null,  null, null, null, null);
                return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(productResponse);
            }
        };
    }
}
