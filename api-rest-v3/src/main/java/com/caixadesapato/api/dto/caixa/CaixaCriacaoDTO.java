package com.caixadesapato.api.dto.caixa;

import com.caixadesapato.api.model.FaixaEtaria;
import com.caixadesapato.api.utils.enums.Genero;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CaixaCriacaoDTO {

    private Genero genero;

    @NotBlank
    @Size(min = 10, max = 500)
    private String carta;

    @NotBlank
    private String url;

    @NotNull
    @Positive
    private Integer quantidade;

    @Positive
    private Integer idFaixaEtaria;

    @NotEmpty
    private int[] itensCaixa = new int[8];

    @NotNull
    @Positive
    private int idPedido;

}
