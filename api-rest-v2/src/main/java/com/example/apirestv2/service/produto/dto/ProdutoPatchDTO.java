package com.example.apirestv2.service.produto.dto;

import com.example.apirestv2.service.produto.enums.EnumGenero;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProdutoPatchDTO {
    @NotBlank
    @Size(min = 5, max = 100)
    private String nome;

    @NotNull
    @Positive
    private Double valor;

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
}
