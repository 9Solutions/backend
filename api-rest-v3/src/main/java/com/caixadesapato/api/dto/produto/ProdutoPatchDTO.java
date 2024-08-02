package com.caixadesapato.api.dto.produto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProdutoPatchDTO {

    @NotBlank
    @Size(min = 5, max = 100)
    private String nome;

    @NotNull
    @Positive
    private Double valor;

}
