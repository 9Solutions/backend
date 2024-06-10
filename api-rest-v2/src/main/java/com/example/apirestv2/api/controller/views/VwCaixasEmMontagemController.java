package com.example.apirestv2.api.controller.views;

import com.example.apirestv2.domain.vwCaixasAtrasadas.VwCaixasAtrasadas;
import com.example.apirestv2.domain.vwCaixasEmMontagem.VwCaixasEmMontagem;
import com.example.apirestv2.service.vwCaixasEmMontagem.VwCaixasEmMontagemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/vw-caixas-em-montagem")
@RequiredArgsConstructor
public class VwCaixasEmMontagemController {
    private final VwCaixasEmMontagemService vwCaixasEmMontagemService;

    @GetMapping
    public List<VwCaixasEmMontagem> findAll() {
        return vwCaixasEmMontagemService.findAll();
    }
}
