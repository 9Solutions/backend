package com.example.apirestv2.domain.produto;

import com.example.apirestv2.service.produto.enums.EnumGenero;
import jakarta.persistence.*;

@Entity
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

    @Column(name = "fk_categoria_produto")
    private Integer categoriaProduto;

    @Column(name = "fk_faixa_etaria")
    private Integer faixaEtaria;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public EnumGenero getGenero() {
        return genero;
    }

    public void setGenero(EnumGenero genero) {
        this.genero = genero;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Integer getCategoriaProduto() {
        return categoriaProduto;
    }

    public void setCategoriaProduto(Integer categoriaProduto) {
        this.categoriaProduto = categoriaProduto;
    }

    public Integer getFaixaEtaria() {
        return faixaEtaria;
    }

    public void setFaixaEtaria(Integer faixaEtaria) {
        this.faixaEtaria = faixaEtaria;
    }
}
