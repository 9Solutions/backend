package com.example.apirestv2.domain.vwQtdPedidosPorFaixaEtaria;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "vw_qtd_pedidos_por_faixa_etaria", schema = "db_9solutions")
public class VwQtdPedidosPorFaixaEtaria {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer id;
    private Integer ano;
    private Integer mes;
    private String faixaNome;
    private Integer qtdPedidos;
}