package com.example.apirestv2.service.statusPedido.dto;

import lombok.*;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
public class StatusPedidoListagem {
    private Integer id;
    private String status;
}
