package com.example.apirestv2.service.caixa.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CaixaUpdateDTO {

    @NotBlank
    @Size(min = 10, max = 500)
    private String carta;

    @NotBlank
    private String url;

    @NotNull
    @Positive
    private Integer quantidade;

}
