package com.example.apirestv2.service.dashUser.autenticacao;

import com.example.apirestv2.domain.dashUser.DashUser;
import com.example.apirestv2.domain.dashUser.repository.DashUserRepository;
import com.example.apirestv2.service.dashUser.autenticacao.dto.DashUserDetalhesDTO;
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

        if (doadorOpt.isEmpty()) {
            throw new UsernameNotFoundException(String.format("Usuário %s não encontrado", username));
        }

        return new DashUserDetalhesDTO(doadorOpt.get());
    }


}
