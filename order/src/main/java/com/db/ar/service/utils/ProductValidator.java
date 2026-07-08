package com.db.ar.service.utils;

import br.com.db.rapid_food_api.product.domain.Product;
import br.com.db.rapid_food_api.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductValidator {

    private final ProductRepository productRepository;

    public Product validate(UUID productId) {
        return productRepository.findById(productId).orElseThrow(
            () -> new EntityNotFoundException("Poduct with id " + productId + " not found"));
    }
}
