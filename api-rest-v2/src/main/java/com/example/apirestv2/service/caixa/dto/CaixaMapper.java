package com.example.apirestv2.service.caixa.dto;

import com.example.apirestv2.domain.caixa.Caixa;
import java.util.List;

public class CaixaMapper {

    public static Caixa toEntity(CaixaCriacaoDTO dto) {
        Caixa caixa = new Caixa();
        caixa.setGenero(dto.getGenero());
        caixa.setCarta(dto.getCarta());
        caixa.setQuantidade(dto.getQuantidade());
        caixa.setDataCriacao(dto.getDataCriacao());
        caixa.setFaixaEtaria(dto.getFaixaEtaria());
        caixa.setUrl(dto.getUrl());
        caixa.setIdPedido(dto.getIdPedido());
        caixa.setDataEntrega(null);

        return caixa;
    }

    public static CaixaListagemDTO toDTO(Caixa caixa){
        CaixaListagemDTO dto = new CaixaListagemDTO();
        dto.setId(caixa.getId());
        dto.setCarta(caixa.getCarta());
        dto.setGenero(caixa.getGenero());
        dto.setUrl(caixa.getUrl());
        dto.setQuantidade(caixa.getQuantidade());
        dto.setDataCriacao(caixa.getDataCriacao());
        dto.setDataEntrega(caixa.getDataEntrega());
        dto.setFaixaEtaria(caixa.getFaixaEtaria());
        dto.setIdPedido(caixa.getIdPedido());

        return dto;
    }

    public static List<CaixaListagemDTO> toDTO(List<Caixa> caixas) {
        return caixas.stream().map(CaixaMapper::toDTO).toList();
    }


}
