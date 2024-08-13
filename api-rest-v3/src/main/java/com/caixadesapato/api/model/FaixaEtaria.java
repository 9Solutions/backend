package com.caixadesapato.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "faixa_etaria", schema = "db_9solutions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    @Column(name = "condicao")
    private Integer condicao;

    @OneToMany(mappedBy = "faixaEtaria")
    private List<Produto> produtos;

}
