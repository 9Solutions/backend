package com.caixadesapato.api.dto.faixaEtaria;

import com.caixadesapato.api.model.FaixaEtaria;

import java.util.List;

public class FaixaEtariaMapper {

    public static FaixaEtaria toEntity(FaixaEtariaCriacaoDTO faixaEtariaCriacaoDTO) {
        return FaixaEtaria.builder()
                .faixaNome(faixaEtariaCriacaoDTO.getFaixaNome())
                .limiteInferior(faixaEtariaCriacaoDTO.getLimiteInferior())
                .limiteSuperior(faixaEtariaCriacaoDTO.getLimiteSuperior())
                .condicao(1)
                .build();
    }

    public static FaixaEtaria toEntity(FaixaEtariaUpdateDTO faixaEtariaUpdateDTO) {
        return FaixaEtaria.builder()
                .faixaNome(faixaEtariaUpdateDTO.getFaixaNome())
                .limiteInferior(faixaEtariaUpdateDTO.getLimiteInferior())
                .limiteSuperior(faixaEtariaUpdateDTO.getLimiteSuperior())
                .build();
    }

    public static FaixaEtariaListagemDTO toDTO(FaixaEtaria faixaEtaria) {
        return FaixaEtariaListagemDTO.builder()
                .id(faixaEtaria.getId())
                .faixaNome(faixaEtaria.getFaixaNome())
                .limiteInferior(faixaEtaria.getLimiteInferior())
                .limiteSuperior(faixaEtaria.getLimiteSuperior())
                .condicao(faixaEtaria.getCondicao())
                .build();
    }

    public static List<FaixaEtariaListagemDTO> toDTO(List<FaixaEtaria> faixaEtarias) {
        return faixaEtarias.stream()
                .map(FaixaEtariaMapper::toDTO)
                .toList();
    }

}
