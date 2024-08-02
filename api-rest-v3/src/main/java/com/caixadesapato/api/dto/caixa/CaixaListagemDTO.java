package com.caixadesapato.api.dto.caixa;

import com.caixadesapato.api.model.FaixaEtaria;
import com.caixadesapato.api.utils.enums.Genero;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CaixaListagemDTO {

    private Integer id;
    private Genero genero;
    private String carta;
    private String url;
    private Integer quantidade;
    private int idPedido;
    private LocalDate dataEntrega;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataCriacao;

    private String faixaEtaria;

    private List<ItemCaixaDTO> itens;

    private List<EtapaCaixaDTO> etapas;

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
