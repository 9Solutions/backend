package com.example.apirestv2.service.caixa;

import com.example.apirestv2.domain.caixa.Caixa;
import com.example.apirestv2.domain.caixa.EtapaCaixa;
import com.example.apirestv2.domain.caixa.repository.EtapaCaixaRepository;
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
