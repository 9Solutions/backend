package com.example.apirestv2.service.dashUser.dto.mapper;

import com.example.apirestv2.domain.dashUser.DashUser;
import com.example.apirestv2.domain.doador.Doador;
import com.example.apirestv2.service.dashUser.autenticacao.dto.DashUserTokenDTO;
import com.example.apirestv2.service.dashUser.dto.DashUserCriacaoDTO;
import com.example.apirestv2.service.dashUser.dto.DashUserListagemDTO;
import com.example.apirestv2.service.doador.autenticacao.dto.DoadorTokenDTO;
import com.example.apirestv2.service.doador.dto.DoadorCriacaoDTO;
import com.example.apirestv2.service.doador.dto.DoadorListagemDTO;
import com.example.apirestv2.service.doador.dto.mapper.DoadorMapper;

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
