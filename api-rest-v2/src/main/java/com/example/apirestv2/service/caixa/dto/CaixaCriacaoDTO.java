package com.example.apirestv2.service.caixa.dto;

import com.example.apirestv2.enums.EnumGenero;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CaixaCriacaoDTO {

    private EnumGenero genero;

    @NotBlank
    @Size(min = 10, max = 500)
    private String carta;

    @NotBlank
    private String url;

    @NotNull
    @Positive
    private Integer quantidade;

    @FutureOrPresent
    private LocalDate dataCriacao;

    @Positive
    private int faixaEtaria;

    @NotEmpty
    private int[] itensCaixa = new int[3];

    @NotNull
    @Positive
    private int idPedido;

}
