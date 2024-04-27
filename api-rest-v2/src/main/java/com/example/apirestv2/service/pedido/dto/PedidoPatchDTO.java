package com.example.apirestv2.service.pedido.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PedidoPatchDTO {
    @NotNull
    private int statusChange;
}
