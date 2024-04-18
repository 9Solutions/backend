package com.example.apirestv2.service.doador.dto.mapper;

import com.example.apirestv2.domain.doador.Doador;
import com.example.apirestv2.service.doador.dto.DoadorCriacaoDTO;
import com.example.apirestv2.service.doador.dto.DoadorListagemDTO;

import java.util.List;

public class DoadorMapper {
    public static DoadorListagemDTO toDto(Doador entity) {
        if (entity == null) return null;

        DoadorListagemDTO dto = new DoadorListagemDTO();

        dto.setId(entity.getId());
        dto.setNomeCompleto(entity.getNomeCompleto());
        dto.setEmail(entity.getEmail());
        dto.setDataCadastro(entity.getDataCadastro());
        // CONTINUAR ESSE MAPPER

        return dto;
    }

    public static Doador toEntity(DoadorCriacaoDTO entity) {
        if (entity == null) return null;

        Doador novoDoador = new Doador();

        novoDoador.setNomeCompleto(entity.getNomeCompleto());
        novoDoador.setEmail(entity.getEmail());
        novoDoador.setSenha(entity.getSenha());
        return novoDoador;
    }

    public static List<DoadorListagemDTO> toDto(List<Doador> entities) {
        if (entities == null) return null;

        return entities.stream().map(DoadorMapper::toDto).toList();

    }
}