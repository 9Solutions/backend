package com.example.apirestv2.service.pedido.dto;


import com.example.apirestv2.service.produto.enums.EnumGenero;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PedidoListagemDTO {

    private Integer id;
    private Double valorTotal;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dataPedido;

    private int statusPedido;
    private int idDoador;
    private List<CaixaDTO> caixas;

    // Nested Class
    @Data
    public static class CaixaDTO {
        private Integer id;
        private Integer quantidade;
        private LocalDate dataEntrega;
    }

}
