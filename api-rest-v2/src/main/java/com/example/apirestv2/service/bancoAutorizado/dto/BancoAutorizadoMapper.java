package com.example.apirestv2.service.bancoAutorizado.dto;

import java.util.List;

public class BancoAutorizadoMapper {
    public static BancoAutorizadoListagemDTO toListagem(BancoAutorizadoExternoDTO dto){
        return BancoAutorizadoListagemDTO.builder()
                .code(dto.getCodigo())
                .fullName(dto.getNomeCompleto())
                .ispb(dto.getIspb())
                .name(dto.getNome())
                .build();
    }

    public static List<BancoAutorizadoListagemDTO> toListagem(List<BancoAutorizadoExternoDTO> dtos) {
        return dtos.stream().map(BancoAutorizadoMapper::toListagem).toList();
    }
}
