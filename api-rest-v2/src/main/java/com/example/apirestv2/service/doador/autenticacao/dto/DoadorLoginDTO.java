package com.example.apirestv2.service.doador.autenticacao.dto;

public class DoadorLoginDTO {
    private String email;
    private String senha;

    public DoadorLoginDTO(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }
}
