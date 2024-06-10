package com.example.apirestv2.api.controller.views;

import com.example.apirestv2.domain.vwCaixasAtrasadas.VwCaixasAtrasadas;
import com.example.apirestv2.domain.vwCaixasParaEntregar.VwCaixasParaEntregar;
import com.example.apirestv2.service.vwCaixasParaEntregar.VwCaixasParaEntregarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/vw-caixas-para-entregar")
@RequiredArgsConstructor
public class VwCaixasParaEntregarController {
    private final VwCaixasParaEntregarService vwCaixasParaEntregarService;

    @GetMapping
    public List<VwCaixasParaEntregar> findAll() {
        return vwCaixasParaEntregarService.findAll();
    }
}
