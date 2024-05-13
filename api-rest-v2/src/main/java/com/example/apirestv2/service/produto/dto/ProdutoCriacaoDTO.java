package com.example.apirestv2.service.produto.dto;

import com.example.apirestv2.service.produto.enums.EnumGenero;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ProdutoCriacaoDTO {

    @NotBlank
    @Size(min = 5, max = 100)
    private String nome;

    private EnumGenero genero;

    @NotNull
    @PositiveOrZero
    private Double valor;

    @NotNull
    @Positive
    private Integer categoriaProduto;

    @Positive
    @NotNull
    private Integer faixaEtaria;

}
