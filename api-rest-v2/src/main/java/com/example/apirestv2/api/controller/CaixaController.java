package com.example.apirestv2.api.controller;

import com.example.apirestv2.service.caixa.CaixaService;
import com.example.apirestv2.service.caixa.dto.CaixaCriacaoDTO;
import com.example.apirestv2.service.caixa.dto.CaixaListagemDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/caixas")
public class CaixaController {

    @Autowired
    private CaixaService service;

    @PostMapping
    public ResponseEntity<CaixaListagemDTO> create(
            @RequestBody @Valid CaixaCriacaoDTO novaCaixa
    ){
        return service.create(novaCaixa);
    }

}
