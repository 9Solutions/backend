package com.caixadesapato.api.dto.pedido;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImportPedidoDTO {
    private int id;
    private int quantidadeCaixas;
}
