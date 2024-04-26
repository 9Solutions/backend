package com.example.apirestv2.service.faixaEtaria.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FaixaEtariaListagemDTO {
    private Integer id;
    private String faixaNome;
    private Integer limiteInferior;
    private Integer limiteSuperior;
}