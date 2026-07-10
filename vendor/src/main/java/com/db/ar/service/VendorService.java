package com.db.ar.service;


import com.db.ar.domain.Vendor;
import com.db.ar.exceptions.DuplicateVendorException;
import com.db.ar.exceptions.VendorNotFoundException;
import com.db.ar.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class VendorService {

    private final VendorRepository vendorRepository;

    @Transactional
    public Vendor create(Vendor vendor) {
        if (vendorRepository.existsByCnpj(vendor.getCnpj())) {
            log.warn("Tentativa de cadastro com CNPJ já existente: {}", vendor.getCnpj());
            throw new DuplicateVendorException("CNPJ já cadastrado");
        }

        return vendorRepository.save(vendor);
    }

    @Transactional(readOnly = true)
    public Vendor findById(Long id) {
        return vendorRepository.findById(id)
                .orElseThrow(() -> new VendorNotFoundException("Restaurante não encontrado com o ID: " + id));
    }

    @Transactional
    public void deactivate(Long id) {
        Vendor vendor = findById(id);
        vendor.deactivate();
        vendorRepository.save(vendor);
        log.info("Restaurante ID {} inativado com sucesso.", id);
    }
}