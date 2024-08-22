package com.caixadesapato.api.controller;

import com.caixadesapato.api.model.StatusCaixa;
import com.caixadesapato.api.service.StatusCaixaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/status-caixa")
@RequiredArgsConstructor
public class StatusCaixaController {

    private final StatusCaixaService service;

    @GetMapping
    public List<StatusCaixa> listAll(){
        return service.listAll();
    }

}
