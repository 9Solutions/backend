package com.example.apirestv2.service.bancoAutorizado.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BancoAutorizadoExternoDTO {
    @JsonProperty("ispb")
    private String ispb;

    @JsonProperty("name")
    private String nome;

    @JsonProperty("code")
    private String codigo;

    @JsonProperty("fullName")
    private String nomeCompleto;
}
