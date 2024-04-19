package com.example.apirestv2.service.caixa.dto;

import com.example.apirestv2.domain.caixa.Caixa;

public class CaixaMapper {

    public static Caixa toEntity(CaixaCriacaoDTO dto) {
        Caixa caixa = new Caixa();
        caixa.setGenero(dto.getGenero());
        caixa.setCarta(dto.getCarta());
        caixa.setDataCriacao(dto.getDataCriacao());
        caixa.setFaixaEtaria(dto.getFaixaEtaria());
        caixa.setUrl(dto.getUrl());
        caixa.setDataEntrega(null);

        return caixa;
    }

    public static CaixaListagemDTO toDTO(Caixa caixa){
        CaixaListagemDTO dto = new CaixaListagemDTO();
        dto.setId(caixa.getId());
        dto.setCarta(caixa.getCarta());
        dto.setGenero(caixa.getGenero());
        dto.setUrl(caixa.getUrl());
        dto.setDataCriacao(caixa.getDataCriacao());
        dto.setDataEntrega(caixa.getDataEntrega());
        dto.setFaixaEtaria(caixa.getFaixaEtaria());

        return dto;
    }

}
