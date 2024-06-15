package com.example.apirestv2.domain.vwQtdDoacoesPorMes;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "vw_qtd_doacoes_por_mes", schema = "db_9solutions")
public class VwQtdDoacoesPorMes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer ano;
    private Integer mes;
    private Integer qtdDoacoes;
}
