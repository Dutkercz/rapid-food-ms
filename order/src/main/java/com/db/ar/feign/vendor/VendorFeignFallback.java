package com.db.ar.feign.vendor;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class VendorFeignFallback implements FallbackFactory<VendorFeignClient> {
    @Override
    public VendorFeignClient create(Throwable cause) {
        return new VendorFeignClient() {
            @Override
            public ResponseEntity<VendorFeignDto> findById(Long vendorId) {
                throw new EntityNotFoundException("Vendor not found or unavailable at the moment.");
            }
        };
    }
}
