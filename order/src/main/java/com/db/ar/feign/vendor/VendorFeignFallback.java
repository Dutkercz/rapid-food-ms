package com.db.ar.feign.vendor;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class VendorFeignFallback implements FallbackFactory<VendorFeignClient> {
    @Override
    public VendorFeignClient create(Throwable cause) {
        return new VendorFeignClient() {
            @Override
            public ResponseEntity<VendorFeignDto> findById(Long vendorId) {
                var response = new VendorFeignDto(vendorId, "Indisponivel", "Indisponivel", null);
                return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(response);
            }
        };
    }
}
