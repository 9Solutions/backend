package com.example.apirestv2.service.dashUser.autenticacao.dto;

import com.example.apirestv2.domain.dashUser.DashUser;
import com.example.apirestv2.domain.doador.Doador;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class DashUserDetalhesDTO implements UserDetails {
    private final String email;
    private final String senha;

    public DashUserDetalhesDTO(DashUser doador) {
        this.email = doador.getEmail();
        this.senha = doador.getSenha();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
