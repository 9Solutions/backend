package com.example.apirestv2.service.produto.dto;

import com.example.apirestv2.service.produto.enums.EnumGenero;
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

    private Integer categoriaProduto;

    private Integer faixaEtaria;

    private EnumGenero genero;

    private int ativo;

}
