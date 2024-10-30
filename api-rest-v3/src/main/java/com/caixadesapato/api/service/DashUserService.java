package com.caixadesapato.api.service;

import com.caixadesapato.api.dto.dash.*;
import com.caixadesapato.api.model.DashUser;
import com.caixadesapato.api.model.view.*;
import com.caixadesapato.api.repository.DashUserRepository;
import com.caixadesapato.api.repository.view.*;
import com.caixadesapato.api.utils.interfaces.ChangeListener;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashUserService {

    private final DashUserRepository dashUserRepository;
    private final VwCaixasAtrasadasRepository vwCaixasAtrasadasRepository;
    private final VwCaixasEmMontagemRepository vwCaixasEmMontagemRepository;
    private final VwCaixasParaEntregarRepository caixasParaEntregarRepository;
    private final VwQtdeDoacoesPorMesRepository vwQtdDoacoesPorMesRepository;
    private final VwQtdePedidosPorFaixaEtariaRepository vwQtdPedidosPorFaixaEtariaRepository;

    public List<VwCaixasAtrasadas> getCaixasAtrasadas() {
        return vwCaixasAtrasadasRepository.findAll();
    }

    public List<VwCaixasEmMontagem> getCaixasEmMontagem() {
        return vwCaixasEmMontagemRepository.findAll();
    }

    public List<VwCaixasParaEntregar> getCaixasParaEntregar() {
        return caixasParaEntregarRepository.findAll();
    }

    public List<VwQtdeDoacoesPorMes> findByAnoAndMes(Integer ano, Integer mes) {
        return vwQtdDoacoesPorMesRepository.findByAnoAndMes(ano, mes);
    }

    public List<VwQtdePedidosPorFaixaEtaria> findByAnoAndMes(Integer ano) {
        return vwQtdPedidosPorFaixaEtariaRepository.findByAno(ano);
    }

}
