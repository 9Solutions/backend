package com.caixadesapato.api.dto.doador;

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
    private String identificador;
    private String email;
    private LocalDate dataCadastro;
    private String telefone;
}
