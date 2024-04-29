package com.example.apirestv2.service.caixa.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CaixaUpdateDTO {

    @NotBlank
    @Size(min = 10, max = 1000)
    private String carta;

    @NotBlank
    private String url;

    @NotNull
    private Integer quantidade;

}
