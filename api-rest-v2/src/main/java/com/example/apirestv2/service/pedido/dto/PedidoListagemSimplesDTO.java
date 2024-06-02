package com.example.apirestv2.service.pedido.dto;


import com.example.apirestv2.domain.statusPedido.StatusPedido;
import com.example.apirestv2.service.statusPedido.dto.StatusPedidoListagem;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PedidoListagemSimplesDTO {

    private Integer id;
    private Double valorTotal;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dataPedido;
    private StatusPedidoDTO statusPedido;

    private DoadorDTO doador;

    @Data
    public static class StatusPedidoDTO {
        private Integer id;
        private String status;
    }

    @Data
    public static class DoadorDTO {
        private Long id;
        private String nome;
        private String telefone;
    }

}
