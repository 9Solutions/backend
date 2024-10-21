package com.caixadesapato.api.dto.doador;

import com.caixadesapato.api.model.Doador;

import java.util.List;

public class DoadorMapper {

    public static DoadorListagemDTO toDto(Doador entity) {
        if (entity == null) return null;

        DoadorListagemDTO dto = new DoadorListagemDTO();

        dto.setId(entity.getId());
        dto.setNomeCompleto(entity.getNomeCompleto());
        dto.setEmail(entity.getEmail());
        dto.setTelefone(entity.getTelefone());

        return dto;
    }

    public static Doador toEntity(DoadorCriacaoDTO entity) {
        if (entity == null) return null;
        Doador novoDoador = new Doador();

        novoDoador.setNomeCompleto(entity.getNomeCompleto());
        novoDoador.setEmail(entity.getEmail());
        novoDoador.setIdentificador(entity.getIdentificador());
        novoDoador.setSenha(entity.getSenha());
        novoDoador.setTelefone(entity.getTelefone());
        novoDoador.setPermissao(entity.getPermissao());

        return novoDoador;
    }

    public static List<DoadorListagemDTO> toDto(List<Doador> entities) {
        if (entities == null) return null;

        return entities.stream().map(DoadorMapper::toDto).toList();
    }

    public static List<Doador> toEntity(List<DoadorCriacaoDTO> entities) {
        if (entities == null) return null;

        return entities.stream().map(DoadorMapper::toEntity).toList();
    }

    public static Doador toEntity(DoadorListagemDTO entity) {
        if (entity == null) return null;

        Doador novoDoador = new Doador();

        novoDoador.setId(entity.getId());
        novoDoador.setNomeCompleto(entity.getNomeCompleto());
        novoDoador.setEmail(entity.getEmail());
        novoDoador.setIdentificador(entity.getIdentificador());
        novoDoador.setTelefone(entity.getTelefone());

        return novoDoador;
    }

    public static DoadorListagemDTO toDto(DoadorCriacaoDTO entity) {
        if (entity == null) return null;

        DoadorListagemDTO dto = new DoadorListagemDTO();

        dto.setNomeCompleto(entity.getNomeCompleto());
        dto.setEmail(entity.getEmail());
        dto.setTelefone(entity.getTelefone());

        return dto;
    }

    public static DoadorTokenDTO toTokenDto(Doador entity, String token) {
        if (entity == null) return null;

        DoadorTokenDTO dto = new DoadorTokenDTO();

        dto.setDoadorId(entity.getId());
        dto.setNome(entity.getNomeCompleto());
        dto.setEmail(entity.getEmail());
        dto.setPermissao(entity.getPermissao());
        dto.setToken(token);

        return dto;
    }

}
