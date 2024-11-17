package com.caixadesapato.api.dto.pedido;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoCriacaoDTO {

    @NotNull
    @PositiveOrZero
    private Double valorTotal;

    private Integer statusPedido;

    @NotNull
    @Positive
    private Long idDoador;

}
