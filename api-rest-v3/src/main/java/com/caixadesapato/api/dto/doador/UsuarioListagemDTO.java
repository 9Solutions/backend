package com.caixadesapato.api.dto.doador;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioListagemDTO {
    private long id;
    private String email;
    private String permissao;
}
