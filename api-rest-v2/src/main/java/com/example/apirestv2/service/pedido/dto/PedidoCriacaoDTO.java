package com.example.apirestv2.service.pedido.dto;

import com.example.apirestv2.domain.statusPedido.StatusPedido;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class PedidoCriacaoDTO {

    @NotNull
    @PositiveOrZero
    private Double valorTotal;

    private StatusPedido statusPedido;

    @NotNull
    @Positive
    private Long idDoador;

}
