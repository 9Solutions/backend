package com.example.apirestv2.api.controller.views;

import com.example.apirestv2.domain.vwCaixasAtrasadas.VwCaixasAtrasadas;
import com.example.apirestv2.service.vwCaixasAtrasadas.VwCaixasAtrasadasService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/vw-caixas-atrasadas")
@RequiredArgsConstructor
public class VwCaixasAtrasadasController {
    private final VwCaixasAtrasadasService vwCaixasAtrasadasService;


    @GetMapping
    public List<VwCaixasAtrasadas> findAll() {
        return vwCaixasAtrasadasService.findAll();
    }
}
