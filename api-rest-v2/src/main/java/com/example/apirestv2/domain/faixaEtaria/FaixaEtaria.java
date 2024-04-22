package com.example.apirestv2.domain.faixaEtaria;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "faixa_etaria", schema = "db_9solutions")
@Getter
@Setter
public class FaixaEtaria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_faixa_etaria")
    private Integer id;
    private String faixaNome;
    private Integer limiteInferior;
    private Integer limiteSuperior;
}
