package com.caixadesapato.api.dto.categoria;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaCriacaoDTO {
    @NotBlank
    private String nome;

    @Positive
    private int estagio;

    @Positive
    private int  qtdeProdutos;
}
