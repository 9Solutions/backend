package com.example.apirestv2.service.pedido.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PedidoListagemSimplesDTO {

    private Integer id;
    private Double valorTotal;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dataPedido;
    private int statusPedido;

    private DoadorDTO doador;

    @Data
    public static class DoadorDTO {
        private Long id;
        private String nome;
        private String telefone;
    }

}
