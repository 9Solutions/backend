package com.example.apirestv2.service.produto.dto;

import com.example.apirestv2.service.produto.enums.EnumGenero;
import lombok.Data;

@Data
public class ProdutoListagemDTO {

    private Integer id;

    private String nome;

    private Double valor;

    private Integer categoriaProduto;

    private Integer faixaEtaria;

    private EnumGenero genero;

    private int ativo;

}
