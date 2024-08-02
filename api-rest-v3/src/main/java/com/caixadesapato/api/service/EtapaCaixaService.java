package com.caixadesapato.api.service;

import com.caixadesapato.api.model.Caixa;
import com.caixadesapato.api.model.EtapaCaixa;
import com.caixadesapato.api.repository.EtapaCaixaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EtapaCaixaService {

    private final EtapaCaixaRepository etapaRepository;

    public EtapaCaixa mudarEtapaCaixa (Caixa caixa, Integer status) {
        EtapaCaixa etapa = new EtapaCaixa();
        etapa.setStatus(status);
        etapa.setCaixa(caixa);
        return etapaRepository.save(etapa);
    }


}
