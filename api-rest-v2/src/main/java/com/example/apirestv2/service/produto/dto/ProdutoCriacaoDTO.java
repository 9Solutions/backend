package com.example.apirestv2.service.produto.dto;

import com.example.apirestv2.service.produto.enums.EnumGenero;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProdutoCriacaoDTO {

    @NotBlank
    @Size(min = 5, max = 100)
    private String nome;

    private EnumGenero genero;

    @NotNull
    @Positive
    private Double valor;

    @NotNull
    @Positive
    private Integer categoriaProduto;

    @Positive
    @NotNull
    private Integer faixaEtaria;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public EnumGenero getGenero() {
        return genero;
    }

    public void setGenero(EnumGenero genero) {
        this.genero = genero;
    }
}
