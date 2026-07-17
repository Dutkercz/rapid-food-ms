package com.db.ar.service;

import com.db.ar.domain.Product;
import com.db.ar.dto.ProductRequestDto;
import com.db.ar.dto.ProductResponseDto;
import com.db.ar.dto.ProductUpdateDto;
import com.db.ar.exception.BusinessException;
import com.db.ar.exception.ProductNotFoundException;
import com.db.ar.exception.VendorNotFoundException;
import com.db.ar.feign.VendorFeignClient;
import com.db.ar.feign.dtos.VendorFeignDto;
import com.db.ar.mapper.ProductMapper;
import com.db.ar.repository.ProductRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final VendorFeignClient vendorFeign;

    @Transactional
    @CacheEvict(value = "productsByVendor", key = "#requestDto.vendorId")
    public ProductResponseDto createProduct(@Valid ProductRequestDto requestDto) {
        if (isInvalidProductPrice(requestDto.price())) {
            throw new BusinessException("Price must be greater than 0");
        }
        var vendor = findVendor(requestDto.vendorId());
        Product product = productMapper.toEntity(requestDto, vendor);
        productRepository.save(product);
        return productMapper.toDto(product);
    }

    @Cacheable(value = "productById", key = "#productId")
    public ProductResponseDto getById(Long productId) {
        Product product =  productRepository.findById(productId).orElseThrow(
                () -> new ProductNotFoundException("Product not found with id: " + productId));
        return productMapper.toDto(product);
    }

    @Cacheable(value = "productsByVendor", key = "#vendorId")
    public List<ProductResponseDto> getAllProductsByVendorId(Long vendorId) {
        findVendor(vendorId);
        return productRepository.findAllByVendorId(vendorId)
                                .stream()
                                .map(productMapper::toDto)
                                .toList();
    }

    @Transactional
    @Caching(put = {@CachePut(value = "productById", key = "#result.id")},
            evict = { @CacheEvict(value = "productsByVendor", key = "#vendorId")}
    )
    public ProductResponseDto updateProduct(ProductUpdateDto updateDto, Long vendorId) {
        var vendor = findVendor(vendorId) ;

        Product product = getOriginalProduct(updateDto.id());

        if (updateDto.price() != null && isInvalidProductPrice(updateDto.price())) {
            throw new BusinessException("Price must be greater than 0");
        }
        if (!Objects.equals(vendor.id(), product.getVendorId())) {
            throw new BusinessException("This product is not owner of this vendor");
        }
        productMapper.updateProduct(product, updateDto);
        product.setUpdatedAt(LocalDateTime.now());
        return productMapper.toDto(product);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "productById", key = "#productId"),
            @CacheEvict(value = "productsByVendor", key = "#vendorId")
    })
    public void deleteProduct(Long vendorId, Long productId) {
        Product product = productRepository.findByIdAndVendorId(productId, vendorId).orElseThrow(() ->
             new ProductNotFoundException("Product with id: " + productId + " not found for this vendor "));
        productRepository.delete(product);
    }

    /// PRIVATE METHODS
    private VendorFeignDto findVendor(Long vendorId){
        ResponseEntity<VendorFeignDto> vendorResponse = vendorFeign.findById(vendorId);
        if (!vendorResponse.getStatusCode().is2xxSuccessful() || vendorResponse.getBody() == null) {
            throw new VendorNotFoundException("Vendor with id " + vendorId + " not found");
        }
        return vendorResponse.getBody();
    }

    private Product getOriginalProduct(Long productId){
        return productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException("Product with id " + productId + " not found"));
    }

    private static boolean isInvalidProductPrice(BigDecimal productPrice) {
        if (productPrice.compareTo(BigDecimal.valueOf(99999999.99)) > 0) {
            throw new BusinessException("Price cannot be greater than 99999999.99");
        }
        return productPrice.compareTo(BigDecimal.ZERO) <= 0;
    }

}
