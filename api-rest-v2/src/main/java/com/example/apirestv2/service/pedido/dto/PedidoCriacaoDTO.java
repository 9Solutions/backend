package com.example.apirestv2.service.pedido.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class PedidoCriacaoDTO {

    @NotNull
    @PositiveOrZero
    private Double valorTotal;

    private int statusPedido;

    @NotNull
    @Positive
    private Long idDoador;

}
