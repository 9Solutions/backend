package com.caixadesapato.api.service;

import com.caixadesapato.api.dto.doador.DoadorDetalhesDTO;
import com.caixadesapato.api.model.Doador;
import com.caixadesapato.api.repository.DoadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AutenticacaoService implements UserDetailsService {
    private final DoadorRepository doadorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Doador> doadorOpt = doadorRepository.findByEmail(username);

        if (doadorOpt.isEmpty()) {
            throw new UsernameNotFoundException(String.format("Usuário %s não encontrado", username));
        }

        return new DoadorDetalhesDTO(doadorOpt.get());
    }
}
