package com.example.apirestv2.service.dashUser.autenticacao.dto;

public class DashUserLoginDTO {
    private String email;
    private String senha;

    public DashUserLoginDTO(String email, String senha) {
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
