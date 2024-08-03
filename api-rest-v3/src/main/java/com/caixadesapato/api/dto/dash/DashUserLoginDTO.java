package com.caixadesapato.api.dto.dash;

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
