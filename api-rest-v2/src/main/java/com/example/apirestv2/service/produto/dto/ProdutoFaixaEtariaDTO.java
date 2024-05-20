package com.example.apirestv2.service.produto.dto;

import lombok.Data;

@Data
public class ProdutoFaixaEtariaDTO {
    private Integer id;
    private String nome;
    private Integer limiteInferior;
    private Integer limiteSuperior;
}
