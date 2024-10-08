package com.example.apirestv2.service.pedido.dto;

import com.example.apirestv2.domain.produto.Produto;
import com.example.apirestv2.enums.EnumGenero;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PedidoListagemDetalhadaDTO {

    private Integer id;

    private DoadorDTO doador;

    @Data
    public static class DoadorDTO {
        private Long id;
        private String nome;
        private String telefone;
    }


    private List<CaixaDTO> caixas;

    @Data
    public static class CaixaDTO {
        private Integer id;
        private String carta;
        private String url;
        private Integer quantidade;
        private EnumGenero genero;
        private int faixaEtaria;

        private List<ItemCaixaDTO> itens;

        private List<EtapaCaixaDTO> etapas;
    }

    @Data
    public static class ItemCaixaDTO {
        private String nome;
    }

    @Data
    public static class EtapaCaixaDTO {
        private Integer status;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDateTime update;
    }

}
