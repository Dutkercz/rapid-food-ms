package com.db.ar.service;

import com.db.ar.domain.Product;
import com.db.ar.dto.ProductRequestDto;
import com.db.ar.dto.ProductResponseDto;
import com.db.ar.dto.ProductUpdateDto;
import com.db.ar.exception.BusinessException;
import com.db.ar.mapper.ProductMapper;
import com.db.ar.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
//    private final VendorRepository vendorRepository;

//    private Vendor findVendorById(UUID id) {
//        return vendorRepository.findById(id)
//               .orElseThrow(() -> new EntityNotFoundException("Vendor with id " + id + " not found"));
//    }

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

    public ProductResponseDto createProduct(@Valid ProductRequestDto requestDto) {
        Product product = productMapper.toEntity(requestDto);
        if (isInvalidProductPrice(product.getPrice())) {
            throw new BusinessException("Price must be greater than 0");
        }
//        Vendor vendor = findVendorById(requestDto.vendorId());
       // product.setVendor(vendor);
        productRepository.save(product);
        return productMapper.toDto(product);
    }

    public List<ProductResponseDto> getAllProducts(UUID vendorId) {
//        findVendorById(vendorId);
        return null;//productRepository.findAllByVendorId(vendorId)
                               // .stream().map(productMapper::toDto).toList();
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
}
