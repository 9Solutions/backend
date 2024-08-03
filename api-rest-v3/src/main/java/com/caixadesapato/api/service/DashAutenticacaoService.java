package com.caixadesapato.api.service;

import com.caixadesapato.api.dto.dash.DashUserDetalhesDTO;
import com.caixadesapato.api.model.DashUser;
import com.caixadesapato.api.repository.DashUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DashAutenticacaoService implements UserDetailsService {

    private final DashUserRepository doadorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<DashUser> doadorOpt = doadorRepository.findByEmail(username);
        System.out.println(doadorOpt.get());

        if (doadorOpt.isEmpty()) {
            throw new UsernameNotFoundException(String.format("Usuário %s não encontrado", username));
        }

        return new DashUserDetalhesDTO(doadorOpt.get());
    }

}
