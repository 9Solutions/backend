package com.caixadesapato.api.dto.pedido;

import com.caixadesapato.api.model.FaixaEtaria;
import com.caixadesapato.api.utils.enums.Genero;
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
        private Genero genero;
        private FaixaEtaria faixaEtaria;

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
