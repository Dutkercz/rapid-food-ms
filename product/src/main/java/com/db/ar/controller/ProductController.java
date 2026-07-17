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
    public ResponseEntity<ProductResponseDto> getById(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.getById(productId));
    }

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<ProductResponseDto>> getAllProductsByVendorId(@PathVariable Long vendorId) {
        return ResponseEntity.ok(productService.getAllProductsByVendorId(vendorId));
    }

    @PatchMapping("/{vendorId}")
    public ResponseEntity<ProductResponseDto> updateProduct(@RequestBody @Valid ProductUpdateDto updateDto,
                                                            @PathVariable Long vendorId) {
        return ResponseEntity.ok(productService.updateProduct(updateDto, vendorId));
    }

    @DeleteMapping("/{vendorId}/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long vendorId, @PathVariable Long productId) {
        productService.deleteProduct(vendorId, productId);
        return ResponseEntity.noContent().build();
    }
}
