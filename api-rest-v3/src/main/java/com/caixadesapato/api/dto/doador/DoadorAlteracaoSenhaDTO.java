package com.caixadesapato.api.dto.doador;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DoadorAlteracaoSenhaDTO {
    private String senhaAtual;
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    @NotBlank(message = "A nova senha não pode ser vazia")
    private String novaSenha;
    private String repetirNovaSenha;
}
