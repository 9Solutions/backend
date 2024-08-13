package com.caixadesapato.api.dto.produto;

import com.caixadesapato.api.utils.enums.Genero;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ProdutoAtualizacaoDTO {

    @NotBlank
    @Size(min = 5, max = 100)
    private String nome;

    private Genero genero;

    @NotNull
    @PositiveOrZero
    private Double valor;

    @NotBlank
    private String urlImagem;

    @NotNull
    @Positive
    private Integer idCategoriaProduto;

    @Positive
    @NotNull
    private Integer idFaixaEtaria;

}
