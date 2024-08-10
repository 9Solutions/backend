package com.caixadesapato.api.dto.doador;

import lombok.Data;

@Data
public class DoadorTokenDTO {

    private Long doadorId;
    private String nome;
    private String email;
    private String permissao;
    private String token;

}
