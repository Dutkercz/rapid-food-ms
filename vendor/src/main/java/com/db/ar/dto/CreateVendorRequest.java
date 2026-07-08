package com.db.ar.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CNPJ;

@Schema(description = "Payload para criação de um novo restaurante parceiro")
public record CreateVendorRequest(

        @Schema(description = "Nome de restaurante", example = "Pizzaria italiana")
        @NotBlank(message = "O nome do restaurante é obrigatório.")
        @Size(max = 150)
        String name,

        @Schema(description = "CNPJ válido do restaurante (Apenas números)", example = "06990590000123")
        @NotBlank(message = "O CNPJ é obrigatório.")
        @CNPJ(message = "Formato de CNPJ inválido.")
        String cnpj
) {}
