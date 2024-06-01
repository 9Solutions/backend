package com.example.apirestv2.service.caixa.dto;

import com.example.apirestv2.domain.categoria.Categoria;
import com.example.apirestv2.enums.EnumGenero;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CaixaListagemDTO {

    private Integer id;
    private EnumGenero genero;
    private String carta;
    private String url;
    private Integer quantidade;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataCriacao;

    private LocalDate dataEntrega;

    private int faixaEtaria;
    private int idPedido;

    private List<ItemCaixaDTO> itens;

    private List<EtapaCaixaDTO> etapas;

    // Nested class
    @Data
    public static class ItemCaixaDTO {
        private Integer id;
        private String nome;
        private Double valor;
    }

    @Data
    public static class EtapaCaixaDTO {
        private Integer status;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDateTime update;
    }

}
