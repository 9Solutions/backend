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
    private int idPedido;

    private Genero genero;
    private String carta;
    private String url;
    private Integer quantidade;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataCriacao;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataEntrega;

    private String faixaEtaria;
    private List<String> itens;
    private List<EtapaCaixaDTO> etapas;

    @Data
    public static class EtapaCaixaDTO {
        private String status;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDateTime update;
    }

}
