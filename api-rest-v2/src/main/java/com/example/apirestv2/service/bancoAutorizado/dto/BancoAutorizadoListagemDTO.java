package com.example.apirestv2.service.bancoAutorizado.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BancoAutorizadoListagemDTO {
    private String ispb;
    private String name;
    private String code;
    private String fullName;
}
