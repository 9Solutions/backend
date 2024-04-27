package com.example.apirestv2.api.controller;

import com.example.apirestv2.domain.caixa.Caixa;
import com.example.apirestv2.domain.itemCaixa.ItemCaixa;
import com.example.apirestv2.service.caixa.CaixaService;
import com.example.apirestv2.service.caixa.dto.CaixaCriacaoDTO;
import com.example.apirestv2.service.caixa.dto.CaixaListagemDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/caixas")
public class CaixaController {

    @Autowired
    private CaixaService service;

    @GetMapping
    public ResponseEntity<List<CaixaListagemDTO>> listAll(){
        return service.listAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CaixaListagemDTO> listByID(@PathVariable Integer id) {
        return service.listByID(id);
    }

    @GetMapping("/{id}/items-caixa")
    public ResponseEntity<List<ItemCaixa>> listByIdItemsCaixa(@PathVariable Integer id) {
        return service.listByIdItemsCaixa(id);
    }

    @PostMapping
    public ResponseEntity<CaixaListagemDTO> create(
            @RequestBody @Valid CaixaCriacaoDTO novaCaixa
    ){
        return service.create(novaCaixa);
    }


}
