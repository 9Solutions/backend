package com.example.apirestv2.service.caixa.dto;

import com.example.apirestv2.domain.caixa.Caixa;
import com.example.apirestv2.domain.itemCaixa.ItemCaixa;

import java.util.ArrayList;
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
        caixa.setDataEntrega(null);
        return caixa;
    }

    public static CaixaListagemDTO toDTO(Caixa caixa) {
        CaixaListagemDTO dto = new CaixaListagemDTO();
        dto.setId(caixa.getId());
        dto.setCarta(caixa.getCarta());
        dto.setGenero(caixa.getGenero());
        dto.setUrl(caixa.getUrl());
        dto.setQuantidade(caixa.getQuantidade());
        dto.setDataCriacao(caixa.getDataCriacao());
        dto.setDataEntrega(caixa.getDataEntrega());
        dto.setFaixaEtaria(caixa.getFaixaEtaria());
        dto.setIdPedido(caixa.getPedido().getId());
        dto.setItens(toListItensDTO(caixa.getItens()));
        return dto;
    }

    public static List<CaixaListagemDTO.ItemCaixaDTO> toListItensDTO(List<ItemCaixa> itens) {
        List<CaixaListagemDTO.ItemCaixaDTO> itensDTO = new ArrayList<>();
        for (ItemCaixa itemDaVez : itens) {
            CaixaListagemDTO.ItemCaixaDTO itemDto = new CaixaListagemDTO.ItemCaixaDTO();
            itemDto.setId(itemDaVez.getProduto().getId());
            itemDto.setNome(itemDaVez.getProduto().getNome());
            itemDto.setValor(itemDaVez.getProduto().getValor());
            itemDto.setCategoriaProduto(itemDaVez.getProduto().getCategoriaProduto());
            itensDTO.add(itemDto);
        }
        return itensDTO;
    }

    public static List<CaixaListagemDTO> toDTO(List<Caixa> caixas) {
        return caixas.stream()
                .map(CaixaMapper::toDTO)
                .toList();
    }

}
