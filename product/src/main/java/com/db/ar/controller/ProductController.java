package com.db.ar.controller;

import com.db.ar.dto.ProductRequestDto;
import com.db.ar.dto.ProductResponseDto;
import com.db.ar.dto.ProductUpdateDto;
import com.db.ar.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody ProductRequestDto requestDto,
                                                            UriComponentsBuilder builder) {
        ProductResponseDto responseDto = productService.createProduct(requestDto);
        URI uri = builder.path("/api/products/{id}").buildAndExpand(responseDto.id()).toUri();
        return ResponseEntity.created(uri).body(responseDto);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> getById(@PathVariable UUID productId) {
        return ResponseEntity.ok(productService.getById(productId));
    }

    @GetMapping("/{vendorId}")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts(@PathVariable UUID vendorId) {
        return ResponseEntity.ok(productService.getAllProducts(vendorId));
    }

    @PatchMapping("/{vendorId}")
    public ResponseEntity<ProductResponseDto> updateProduct(@RequestBody @Valid ProductUpdateDto updateDto,
                                                            @PathVariable UUID vendorId) {
        return ResponseEntity.ok(productService.updateProduct(updateDto, vendorId));
    }

    @DeleteMapping("/{vendorId}/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID vendorId, @PathVariable UUID productId) {
        productService.deleteProduct(vendorId, productId);
        return ResponseEntity.noContent().build();
    }
}
