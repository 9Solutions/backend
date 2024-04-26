package com.example.apirestv2.service.doador.autenticacao.dto;

import com.example.apirestv2.domain.doador.Doador;
import lombok.Getter;
import org.apache.catalina.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class DoadorDetalhesDTO implements UserDetails {
    private final String nome;
    private final String email;
    private final String senha;

    public DoadorDetalhesDTO(Doador doador) {
        this.nome = doador.getNomeCompleto();
        this.email = doador.getEmail();
        this.senha = doador.getSenha();
    }

    public String getNome() {
        return nome;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}

