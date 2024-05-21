package com.example.apirestv2.service.produto.dto;


import com.example.apirestv2.domain.categoria.Categoria;
import com.example.apirestv2.domain.faixaEtaria.FaixaEtaria;
import com.example.apirestv2.enums.EnumGenero;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoListagemDTO {

    private Integer id;

    private String nome;

    private Double valor;

    private EnumGenero genero;

    private int ativo;

    private CategoriaProdutoDTO categoria;

    private FaixaEtariaDTO faixaEtaria;

    @Data
    public static class CategoriaProdutoDTO {
        private Integer id;
        private String nome;
    }

    @Data
    public static class FaixaEtariaDTO {
        private Integer id;
        private String faixaNome;
        private Integer limiteInferior;
        private Integer limiteSuperior;
    }
}
