package com.example.apirestv2.api.controller.views;

import com.example.apirestv2.domain.vwQtdPedidosPorFaixaEtaria.VwQtdPedidosPorFaixaEtaria;
import com.example.apirestv2.service.vwQtdPedidosPorFaixaEtaria.VwQtdPedidosPorFaixaEtariaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/vw-qtd-pedidos-por-faixa-etaria")
@RequiredArgsConstructor
public class VwQtdPedidosPorFaixaEtariaController {
    private final VwQtdPedidosPorFaixaEtariaService vwQtdPedidosPorFaixaEtariaService;

    @GetMapping("/{ano}")
    public List<VwQtdPedidosPorFaixaEtaria> findAll(@PathVariable Integer ano) {
        return vwQtdPedidosPorFaixaEtariaService.findByAnoAndMes(ano);
    }
}
