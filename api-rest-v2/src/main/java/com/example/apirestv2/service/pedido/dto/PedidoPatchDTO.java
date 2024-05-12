package com.example.apirestv2.service.pedido.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PedidoPatchDTO {
    @NotNull
    @Positive
    private int statusChange;
}
