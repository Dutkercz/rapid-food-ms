package com.db.ar.feign.vendor;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class VendorFeignClientFallback implements FallbackFactory<VendorFeignClient> {
    @Override
    public VendorFeignClient create(Throwable cause) {
        return new VendorFeignClient() {
            @Override
            public ResponseEntity<VendorFeignDto> findById(Long vendorId) {
                var vendorFallback = new VendorFeignDto(vendorId, "INDISPONIVEL", "INDISPONIVEL",
                                                        null);
                return ResponseEntity.ok().body(vendorFallback);
            }
        };
    }
}
