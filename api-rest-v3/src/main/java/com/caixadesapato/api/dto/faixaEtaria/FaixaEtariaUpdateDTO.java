package com.caixadesapato.api.dto.faixaEtaria;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FaixaEtariaUpdateDTO {

    @NotBlank
    private String faixaNome;

    @Min(0)
    private Integer limiteInferior;

    @Min(0)
    private Integer limiteSuperior;

    @AssertTrue(message = "O limite inferior deve ser menor que o limite superior")
    public boolean checkLimite() {
        return limiteInferior < limiteSuperior;
    }

}
