package com.example.apirestv2.service.pedido.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PedidoCriacaoDTO {

    @NotNull
    @PositiveOrZero
    private Double valorTotal;

    @NotNull
    @Positive
    private int statusPedido;

    @NotNull
    @Positive
    private int idDoador;

}
