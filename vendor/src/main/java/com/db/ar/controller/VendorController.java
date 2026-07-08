package com.db.ar.controller;

import com.db.ar.domain.Vendor;
import com.db.ar.dto.CreateVendorRequest;
import com.db.ar.dto.VendorResponse;
import com.db.ar.mapper.VendorMapper;
import com.db.ar.service.VendorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/vendors")
@RequiredArgsConstructor
@Tag(name = "Gestão de Restaurantes", description = "Endpoints para gerenciar os restaurantes parceiros")
public class VendorController {

    private final VendorService vendorService;
    private final VendorMapper vendorMapper;

    @Operation(summary = "Cadastrar um novo restaurante", description = "Cria um restaurante parceiro e o ativa por padrao.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurante criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos (ex: cnpj mal formatado)", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflito: cnpj já cadastrado", content = @Content)
    })
    @PostMapping
    public ResponseEntity<VendorResponse> create(@RequestBody @Valid CreateVendorRequest request) {
        Vendor vendorToSave = vendorMapper.toEntity(request);
        Vendor savedVendor = vendorService.create(vendorToSave);
        VendorResponse response = vendorMapper.toResponse(savedVendor);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Buscar um restaurante pelo ID", description = "Retorna os detalhes de um restaurante especifico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurante encontrado"),
            @ApiResponse(responseCode = "404", description = "Restaurante nao encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<VendorResponse> findById(@PathVariable UUID id) {
        Vendor vendor = vendorService.findById(id);
        return ResponseEntity.ok(vendorMapper.toResponse(vendor));
    }

    @Operation(summary = "Inativar um restaurante", description = "Altera o status do restaurante para inativo. Não é possível inativar um restaurante já inativo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Restaurante inativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflito: Restaurante já está inativo", content = @Content)
    })
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable UUID id) {
        vendorService.deactivate(id);
        return ResponseEntity.noContent().build();
    }
    
}
