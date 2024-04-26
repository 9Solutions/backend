package com.example.apirestv2.service.caixa.dto;

import com.example.apirestv2.service.produto.enums.EnumGenero;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CaixaCriacaoDTO {

    private EnumGenero genero;

    @NotBlank
    @Size(min = 10, max = 1000)
    private String carta;

    @NotBlank
    private String url;

    @FutureOrPresent
    private LocalDate dataCriacao;

    @Positive
    private int faixaEtaria;

    @NotEmpty
    private int[] itensCaixa = new int[3];

    public EnumGenero getGenero() {
        return genero;
    }

    public void setGenero(EnumGenero genero) {
        this.genero = genero;
    }

    public String getCarta() {
        return carta;
    }

    public void setCarta(String carta) {
        this.carta = carta;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public int getFaixaEtaria() {
        return faixaEtaria;
    }

    public void setFaixaEtaria(int faixaEtaria) {
        this.faixaEtaria = faixaEtaria;
    }

    public int[] getItensCaixa() {
        return itensCaixa;
    }

    public void setItensCaixa(int[] itensCaixa) {
        this.itensCaixa = itensCaixa;
    }
}
