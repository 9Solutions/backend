package com.example.apirestv2.service.caixa.dto;

import com.example.apirestv2.service.produto.enums.EnumGenero;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Data
public class CaixaListagemDTO {

    private Integer id;
    private EnumGenero genero;
    private String carta;
    private String url;
    private Integer quantidade;
    private LocalDate dataCriacao;
    private LocalDate dataEntrega;
    private int faixaEtaria;
    private int idPedido;

}
