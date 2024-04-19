package com.example.apirestv2.domain.caixa;

import com.example.apirestv2.service.produto.enums.EnumGenero;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "caixa", schema = "db_9solutions")
public class Caixa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_caixa")
    private Integer id;

    @Column(name = "genero")
    private EnumGenero genero;

    @Column(name = "carta")
    private String carta;

    @Column(name = "url")
    private String url;

    @Column(name = "dt_criacao")
    @CreationTimestamp
    private LocalDate dataCriacao;

    @Column(name = "dt_entrega")
    private LocalDate dataEntrega;

    @Column(name = "fk_faixa_etaria")
    private int faixaEtaria;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public LocalDate getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(LocalDate dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public int getFaixaEtaria() {
        return faixaEtaria;
    }

    public void setFaixaEtaria(int faixaEtaria) {
        this.faixaEtaria = faixaEtaria;
    }
}
