package com.example.apirestv2.service.doador.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoadorCriacaoDTO {

    @NotBlank
    @Size(min = 5, max = 100)
    private String nomeCompleto;

    @NotBlank
    @CPF
    @Size(min = 11, max = 14)
    private String identificador;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(max = 20)
    private String telefone;

    @NotBlank
    @Size(min = 8, max = 30)
    private String senha;

}
