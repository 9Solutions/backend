package com.caixadesapato.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "faixa_etaria", schema = "db_9solutions")
@Getter
@Setter
public class FaixaEtaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_faixa_etaria")
    private Integer id;

    @Column(name = "faixa_nome")
    private String faixaNome;

    @Column(name = "limite_inferior")
    private Integer limiteInferior;

    @Column(name = "limite_superior")
    private Integer limiteSuperior;

    @OneToMany(mappedBy = "faixaEtaria")
    private List<Produto> produtos;

}
