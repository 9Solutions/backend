package com.caixadesapato.api.dto.pedido;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PedidoPatchDTO {

    @NotNull
    @Positive
    private int statusChange;

}
