package com.example.apirestv2.service.vwQtdPedidosPorFaixaEtaria;

import com.example.apirestv2.domain.vwQtdPedidosPorFaixaEtaria.VwQtdPedidosPorFaixaEtaria;
import com.example.apirestv2.domain.vwQtdPedidosPorFaixaEtaria.repository.VwQtdPedidosPorFaixaEtariaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VwQtdPedidosPorFaixaEtariaService {
    private final VwQtdPedidosPorFaixaEtariaRepository vwQtdPedidosPorFaixaEtariaRepository;

    public List<VwQtdPedidosPorFaixaEtaria> findByAnoAndMes(Integer ano) {
        return vwQtdPedidosPorFaixaEtariaRepository.findByAno(ano);
    }
}
