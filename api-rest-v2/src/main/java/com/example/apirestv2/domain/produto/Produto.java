package com.example.apirestv2.domain.produto;

import com.example.apirestv2.domain.categoria.Categoria;
import com.example.apirestv2.domain.faixaEtaria.FaixaEtaria;
import com.example.apirestv2.enums.EnumGenero;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "produto", schema = "db_9solutions")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produto")
    private Integer id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "genero")
    @Enumerated(value = EnumType.STRING)
    private EnumGenero genero;

    @Column(name = "valor")
    private Double valor;

    @Column(name = "ativo")
    private int ativo;

    @ManyToOne
    @JoinColumn(name = "fk_categoria_produto")
    private Categoria categoriaProduto;

    @ManyToOne
    @JoinColumn(name = "fk_faixa_etaria")
    private FaixaEtaria faixaEtaria;
}
