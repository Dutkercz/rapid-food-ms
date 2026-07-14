package com.db.ar.service;

import com.db.ar.feign.product.ProductFeignClient;
import com.db.ar.feign.product.ProductFeignDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductIntegrationService {

    private final ProductFeignClient productFeign;

    @Cacheable(value = "products", key = "#productId", unless = "#result == null")
    public ProductFeignDto findProductById(Long productId) {
        try {
            ResponseEntity<ProductFeignDto> response = productFeign.getById(productId);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody();
            }
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        } catch (Exception e) {
            log.error("Erro ao buscar produto {}, usando fallback do Feign", productId, e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product unavailable");
        }
    }
}