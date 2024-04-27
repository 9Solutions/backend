package com.example.apirestv2.service.pedido.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PedidoListagemDTO {

    private Integer id;
    private Double valorTotal;
    private LocalDateTime dataPedido;
    private int statusPedido;
    private int idDoador;

}
