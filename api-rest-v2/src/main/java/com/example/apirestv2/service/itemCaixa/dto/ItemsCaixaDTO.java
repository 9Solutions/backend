package com.example.apirestv2.service.itemCaixa.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class ItemsCaixaDTO {

    private Integer id;
    private int idCaixa;
    private int idProduto;

}
