package com.caixadesapato.api.dto.dash;

import com.caixadesapato.api.model.DashUser;

import java.util.List;

public class DashUserMapper {

    public static DashUserListagemDTO toDto(DashUser entity) {
        if (entity == null) return null;

        DashUserListagemDTO dto = new DashUserListagemDTO();

        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setSenha(entity.getSenha());

        return dto;
    }

    public static DashUser toEntity(DashUserCriacaoDTO entity) {
        if (entity == null) return null;

        DashUser novoDoador = new DashUser();

        novoDoador.setEmail(entity.getEmail());
        novoDoador.setSenha(entity.getSenha());

        return novoDoador;
    }

    public static List<DashUserListagemDTO> toDto(List<DashUser> entities) {
        if (entities == null) return null;

        return entities.stream().map(DashUserMapper::toDto).toList();
    }

    public static List<DashUser> toEntity(List<DashUserCriacaoDTO> entities) {
        if (entities == null) return null;

        return entities.stream().map(DashUserMapper::toEntity).toList();
    }

    public static DashUser toEntity(DashUserListagemDTO entity) {
        if (entity == null) return null;

        DashUser novoDoador = new DashUser();

        novoDoador.setId(entity.getId());
        novoDoador.setEmail(entity.getEmail());
        novoDoador.setSenha(entity.getSenha());

        return novoDoador;
    }

    public static DashUserListagemDTO toDto(DashUserCriacaoDTO entity) {
        if (entity == null) return null;

        DashUserListagemDTO dto = new DashUserListagemDTO();

        dto.setEmail(entity.getEmail());
        dto.setSenha(entity.getSenha());

        return dto;
    }

    public static DashUserTokenDTO toTokenDto(DashUser entity, String token) {
        if (entity == null) return null;

        DashUserTokenDTO dto = new DashUserTokenDTO();

        dto.setDashUserId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setToken(token);

        return dto;
    }

}
