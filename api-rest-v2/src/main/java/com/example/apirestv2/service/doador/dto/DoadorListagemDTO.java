package com.example.apirestv2.service.doador.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoadorListagemDTO {
    private Long id;
    private String nomeCompleto;
    private int identificador;
    private String email;
    private LocalDate dataCadastro;
    private String telefone;
    private String senha;

}
