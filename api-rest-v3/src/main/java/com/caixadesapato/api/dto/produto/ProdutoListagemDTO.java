package com.caixadesapato.api.dto.produto;

import com.caixadesapato.api.utils.enums.Genero;
import lombok.AllArgsConstructor;
import lombok.Builder;
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

    private String urlImagem;

    private Genero genero;

    private int condicao;

    private CategoriaProdutoDTO categoria;

    private FaixaEtariaDTO faixaEtaria;

    @Data
    public static class CategoriaProdutoDTO {
        private Integer id;
        private String nome;
        private Integer condicao;
    }

    @Data
    public static class FaixaEtariaDTO {
        private Integer id;
        private String faixaNome;
        private Integer limiteInferior;
        private Integer limiteSuperior;
        private Integer condicao;
    }

}
