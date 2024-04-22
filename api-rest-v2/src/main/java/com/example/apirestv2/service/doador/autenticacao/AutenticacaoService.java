package com.example.apirestv2.service.doador.autenticacao;

import com.example.apirestv2.domain.doador.Doador;
import com.example.apirestv2.domain.doador.repository.DoadorRepository;
import com.example.apirestv2.service.doador.autenticacao.dto.DoadorDetalhesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class AutenticacaoService implements UserDetailsService {
    @Autowired
    private DoadorRepository doadorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Doador doadorOpt = doadorRepository.findByEmail(username);

        if (doadorOpt == null) {
            throw new UsernameNotFoundException(String.format("Usuário %s não encontrado", username));
        }

        return new DoadorDetalhesDTO(doadorOpt);
    }


}
