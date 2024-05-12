package com.example.apirestv2.service.caixa.dto;

import com.example.apirestv2.domain.produto.Produto;
import com.example.apirestv2.service.produto.enums.EnumGenero;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;

@Data
public class CaixaListagemDTO {

    private Integer id;
    private EnumGenero genero;
    private String carta;
    private String url;
    private Integer quantidade;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dataCriacao;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dataEntrega;

    private int faixaEtaria;
    private int idPedido;

    private List<ItemCaixaDTO> itens;

    // Nested class
    @Data
    public static class ItemCaixaDTO {
        private Integer id;
        private String nome;
        private Double valor;
        private Integer categoriaProduto;
    }

}
