package com.example.apirestv2.api.controller.views;

import com.example.apirestv2.domain.vwQtdDoacoesPorMes.VwQtdDoacoesPorMes;
import com.example.apirestv2.service.vwQtdDoacoesPorMes.VwQtdDoacoesPorMesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/vw-qtd-doacoes-por-mes")
@RequiredArgsConstructor
public class VwQtdDoacoesPorMesController {
    private final VwQtdDoacoesPorMesService vwQtdDoacoesPorMesService;

    @GetMapping("/{ano}/{mes}")
    public List<VwQtdDoacoesPorMes> findAll(@PathVariable Integer ano, @PathVariable Integer mes) {
        return vwQtdDoacoesPorMesService.findByAnoAndMes(ano, mes);
    }
}
