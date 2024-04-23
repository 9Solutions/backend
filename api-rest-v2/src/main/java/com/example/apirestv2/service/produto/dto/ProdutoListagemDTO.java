package com.example.apirestv2.service.produto.dto;

import com.example.apirestv2.service.produto.enums.EnumGenero;

public class ProdutoListagemDTO {

    private Integer id;

    private String nome;

    private Double valor;

    private Integer categoriaProduto;

    private Integer faixaEtaria;

    private EnumGenero genero;

    private int ativo;

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

    public int getAtivo() {
        return ativo;
    }

    public void setAtivo(int ativo) {
        this.ativo = ativo;
    }
}
