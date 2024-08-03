package com.caixadesapato.api.service;

import com.caixadesapato.api.model.Caixa;
import com.caixadesapato.api.model.EtapaCaixa;
import com.caixadesapato.api.model.StatusCaixa;
import com.caixadesapato.api.repository.EtapaCaixaRepository;
import com.caixadesapato.api.repository.StatusCaixaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EtapaCaixaService {

    private final EtapaCaixaRepository etapaRepository;
    private final StatusCaixaRepository statusCaixaRepository;

    public EtapaCaixa mudarEtapaCaixa (Caixa caixa, Integer status) {
        EtapaCaixa etapa = new EtapaCaixa();
        Optional<StatusCaixa> statusCaixa = statusCaixaRepository.findById(status);
        etapa.setStatus(statusCaixa.get());
        etapa.setCaixa(caixa);
        return etapaRepository.save(etapa);
    }

}
