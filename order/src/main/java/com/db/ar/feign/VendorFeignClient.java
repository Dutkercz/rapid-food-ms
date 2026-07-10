package com.db.ar.feign;

import com.db.ar.feign.dtos.VendorFeignDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:8085/api/vendors", name = "order.vemdors")
public interface VendorFeignClient {

    @GetMapping("/{vendorId}")
    ResponseEntity<VendorFeignDto> findById(@PathVariable Long vendorId);
}
