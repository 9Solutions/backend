package com.example.apirestv2.service.caixa.dto;

import com.example.apirestv2.service.produto.enums.EnumGenero;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CaixaCriacaoDTO {

    private EnumGenero genero;

    @NotBlank
    @Size(min = 10, max = 1000)
    private String carta;

    @NotBlank
    private String url;

    @NotNull
    private Integer quantidade;

    @FutureOrPresent
    private LocalDate dataCriacao;

    @Positive
    private int faixaEtaria;

    @NotEmpty
    private int[] itensCaixa = new int[3];

    @NotNull
    private int idPedido;

}
