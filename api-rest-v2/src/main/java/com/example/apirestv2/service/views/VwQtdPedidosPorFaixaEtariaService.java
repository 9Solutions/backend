package com.example.apirestv2.service.views;

import com.example.apirestv2.domain.vwQtdPedidosPorFaixaEtaria.repository.VwQtdPedidosPorFaixaEtariaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VwQtdPedidosPorFaixaEtariaService {
    private final VwQtdPedidosPorFaixaEtariaRepository vwQtdPedidosPorFaixaEtariaRepository;
}
