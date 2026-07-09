package com.db.ar.service;

import com.db.ar.domain.Product;
import com.db.ar.dto.ProductRequestDto;
import com.db.ar.dto.ProductResponseDto;
import com.db.ar.dto.ProductUpdateDto;
import com.db.ar.exception.BusinessException;
import com.db.ar.feign.VendorFeignClient;
import com.db.ar.feign.dtos.VendorFeignDto;
import com.db.ar.mapper.ProductMapper;
import com.db.ar.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final VendorFeignClient vendorFeign;

    private VendorFeignDto findVendorById(UUID vendorId) {
        var response = vendorFeign.findById(vendorId);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vendor with id " + vendorId + " not found");
        }
    }

    private Product findProduct(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
    }

    private static boolean isInvalidProductPrice(BigDecimal productPrice) {
        if (productPrice.compareTo(BigDecimal.valueOf(99999999.99)) > 0) {
            throw new BusinessException("Price cannot be greater than 99999999.99");
        }
        return productPrice.compareTo(BigDecimal.ZERO) <= 0;
    }

    @Transactional
    public ProductResponseDto createProduct(@Valid ProductRequestDto requestDto) {
        if (isInvalidProductPrice(requestDto.price())) {
            throw new BusinessException("Price must be greater than 0");
        }
        var vendor = findVendorById(requestDto.vendorId());
        var product = productMapper.toEntity(requestDto, vendor);
        productRepository.save(product);
        return productMapper.toDto(product);
    }

    public List<ProductResponseDto> getAllProducts(UUID vendorId) {
        findVendorById(vendorId);
        return productRepository.findAllByVendorId(vendorId)
                                .stream().map(productMapper::toDto).toList();
    }

    @Transactional
    public ProductResponseDto updateProduct(@Valid ProductUpdateDto updateDto, UUID vendorId) {
//        Vendor vendor = findVendorById(vendorId);
        Product product = findProduct(updateDto.id());
        if (updateDto.price() != null && isInvalidProductPrice(updateDto.price())){
            throw new BusinessException("Price must be greater than 0");
        }
//        if (vendor.getId() != product.getVendor().getId()) {
//            throw new BusinessException("This product is not owner of this vendor");
//        }
        productMapper.updateProduct(product, updateDto);
        product.setUpdatedAt(LocalDateTime.now());
        return productMapper.toDto(product);
    }

    @Transactional
    public void deleteProduct(UUID vendorId, UUID productId) {
//        Product product = productRepository.findByIdAndVendorId(productId, vendorId).orElseThrow(() ->
//                 new EntityNotFoundException("Product with this ID and vendor ID found"));
//        productRepository.delete(product);

    }

    public ProductResponseDto getById(UUID productId) {
        return productMapper.toDto(findProduct(productId));
    }
}
