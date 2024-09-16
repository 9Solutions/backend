package com.caixadesapato.api.dto.pedido;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PedidoListagemSimplesDTO {

    private Integer id;
    private Double valorTotal;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dataPedido;
    private StatusPedidoDTO statusPedido;
    private int quantidadeCaixas;

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
