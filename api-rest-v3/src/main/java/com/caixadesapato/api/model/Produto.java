package com.caixadesapato.api.model;

import com.caixadesapato.api.utils.enums.Genero;
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
    private Genero genero;

    @Column(name = "valor")
    private Double valor;

    @Column(name = "url_imagem")
    private String urlImagem;

    @Column(name = "condicao")
    private int condicao;

    @ManyToOne
    @JoinColumn(name = "fk_categoria_produto")
    private Categoria categoriaProduto;

    @ManyToOne
    @JoinColumn(name = "fk_faixa_etaria")
    private FaixaEtaria faixaEtaria;

}
