package com.caixadesapato.api.dto.caixa;

import com.caixadesapato.api.model.Caixa;
import com.caixadesapato.api.model.EtapaCaixa;
import com.caixadesapato.api.model.ItemCaixa;

import java.util.ArrayList;
import java.util.List;

public class CaixaMapper {

    public static Caixa toEntity(CaixaCriacaoDTO dto) {
        Caixa caixa = new Caixa();
        caixa.setGenero(dto.getGenero());
        caixa.setCarta(dto.getCarta());
        caixa.setQuantidade(dto.getQuantidade());
        caixa.setUrl(dto.getUrl());
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
        dto.setQrCodeToken(caixa.getQrCodeToken());

        dto.setFaixaEtaria(caixa.getFaixaEtaria().getFaixaNome());
        dto.setIdPedido(caixa.getPedido().getId());
        dto.setItens(toListItensDTO(caixa.getItens()));
        dto.setEtapas(toListEtapasDTO(caixa.getEtapas()));

        return dto;
    }

    public static List<String> toListItensDTO(List<ItemCaixa> itens) {
        return itens.stream().map(item -> item.getProduto().getNome()).toList();
    }

    public static List<CaixaListagemDTO> toDTO(List<Caixa> caixas) {
        return caixas.stream()
                .map(CaixaMapper::toDTO)
                .toList();
    }

    public static List<CaixaListagemDTO.EtapaCaixaDTO> toListEtapasDTO(List<EtapaCaixa> etapas) {
        List<CaixaListagemDTO.EtapaCaixaDTO> etapasDTO = new ArrayList<>();
        etapas.stream().forEach(
                etapa -> {
                    CaixaListagemDTO.EtapaCaixaDTO etapaDTO = new CaixaListagemDTO.EtapaCaixaDTO();
                    etapaDTO.setId(etapa.getStatus().getId());
                    etapaDTO.setStatus(etapa.getStatus().getStatus());
                    etapaDTO.setUpdate(etapa.getUpdateAt());
                    etapasDTO.add(etapaDTO);
                }
        );
        return etapasDTO;
    }

}
