package com.db.ar.service;

import com.db.ar.feign.vendor.VendorFeignClient;
import com.db.ar.feign.vendor.VendorFeignDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class VendorIntegrationService {

    private final VendorFeignClient vendorFeign;

    @Cacheable(value = "vendors", key = "#vendorId", unless = "#result == null")
    public VendorFeignDto findVendorById(Long vendorId) {
        try {
            var response = vendorFeign.findById(vendorId);
            if (response.getBody() != null && response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            }
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vendor Not Found");
        }catch (Exception e){
            log.error("Erro ao buscar produto {}, usando fallback do Feign", vendorId, e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vendor unavailable");
        }
    }
}
