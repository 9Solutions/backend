package com.caixadesapato.api.dto.doador;

public class DoadorTokenDTO {
    private Long doadorId;
    private String nome;
    private String email;
    private String token;

    public DoadorTokenDTO(Long doadorId, String nome, String email, String token) {
        this.doadorId = doadorId;
        this.nome = nome;
        this.email = email;
        this.token = token;
    }

    public DoadorTokenDTO() {
    }

    public Long getDoadorId() {
        return doadorId;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public void setDoadorId(Long doadorId) {
        this.doadorId = doadorId;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
