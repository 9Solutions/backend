package com.example.apirestv2.service.pedido.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PedidoCriacaoDTO {

    @NotNull
    private Double valorTotal;

    @NotNull
    private int statusPedido;

    @NotNull
    private int idDoador;

}
