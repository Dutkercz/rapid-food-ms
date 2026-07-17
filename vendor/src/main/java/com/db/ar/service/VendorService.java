package com.db.ar.service;


import com.db.ar.domain.Vendor;
import com.db.ar.dto.VendorResponse;
import com.db.ar.exceptions.DuplicateVendorException;
import com.db.ar.exceptions.VendorNotFoundException;
import com.db.ar.mapper.VendorMapper;
import com.db.ar.mapper.VendorOrderMapper;
import com.db.ar.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VendorService {

    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;
    private final VendorOrderMapper vendorOrderMapper;

    @Transactional
    public Vendor create(Vendor vendor) {
        if (vendorRepository.existsByCnpj(vendor.getCnpj())) {
            log.warn("Tentativa de cadastro com CNPJ já existente: {}", vendor.getCnpj());
            throw new DuplicateVendorException("CNPJ já cadastrado");
        }
        return vendorRepository.save(vendor);
    }

    public Vendor findById(Long id) {
        return vendorRepository.findById(id)
                .orElseThrow(() ->
                                 new VendorNotFoundException("Restaurante não encontrado com o ID: " + id));
    }

    @Transactional
    public void deactivate(Long id) {
        Vendor vendor = findById(id);
        vendor.deactivate();
        vendorRepository.save(vendor);
        log.info("Restaurante ID {} inativado com sucesso.", id);
    }


    public List<VendorResponse> findAll() {
        return vendorRepository.findAll().stream().map(vendorMapper::toResponse).toList();
    }
}