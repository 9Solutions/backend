package com.caixadesapato.api.dto.caixa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
