package com.example.apirestv2.service.faixaEtaria.dto;

import com.example.apirestv2.domain.faixaEtaria.FaixaEtaria;

import java.util.List;

public class FaixaEtariaMapper {
    public static FaixaEtaria toEntity(FaixaEtariaCriacaoDTO faixaEtariaCriacaoDTO) {
        FaixaEtaria faixaEtaria = new FaixaEtaria();
        faixaEtaria.setFaixaNome(faixaEtariaCriacaoDTO.getFaixaNome());
        faixaEtaria.setLimiteInferior(faixaEtariaCriacaoDTO.getLimiteInferior());
        faixaEtaria.setLimiteSuperior(faixaEtariaCriacaoDTO.getLimiteSuperior());

        return faixaEtaria;
    }

    public static FaixaEtaria toEntity(FaixaEtariaUpdateDTO faixaEtariaUpdateDTO) {
        FaixaEtaria faixaEtaria = new FaixaEtaria();
        faixaEtaria.setFaixaNome(faixaEtariaUpdateDTO.getFaixaNome());
        faixaEtaria.setLimiteInferior(faixaEtariaUpdateDTO.getLimiteInferior());
        faixaEtaria.setLimiteSuperior(faixaEtariaUpdateDTO.getLimiteSuperior());

        return faixaEtaria;
    }

    public static FaixaEtariaListagemDTO toDTO(FaixaEtaria faixaEtaria) {
        return FaixaEtariaListagemDTO.builder()
                .id(faixaEtaria.getId())
                .faixaNome(faixaEtaria.getFaixaNome())
                .limiteInferior(faixaEtaria.getLimiteInferior())
                .limiteSuperior(faixaEtaria.getLimiteSuperior())
                .build();
    }

    public static List<FaixaEtariaListagemDTO> toDTO(List<FaixaEtaria> faixaEtarias) {
        return faixaEtarias.stream().map(FaixaEtariaMapper::toDTO).toList();
    }

}
