package com.example.apirestv2.api.controller;

import com.example.apirestv2.domain.caixa.Caixa;
import com.example.apirestv2.domain.itemCaixa.ItemCaixa;
import com.example.apirestv2.service.caixa.CaixaService;
import com.example.apirestv2.service.caixa.dto.CaixaCriacaoDTO;
import com.example.apirestv2.service.caixa.dto.CaixaListagemDTO;
import com.example.apirestv2.service.caixa.dto.CaixaMapper;
import com.example.apirestv2.service.caixa.dto.CaixaUpdateDTO;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listando as caixas"),
            @ApiResponse(responseCode = "204", description = "Nenhuma caixa cadastrada"),
    })
    public ResponseEntity<List<CaixaListagemDTO>> listAll(){
        return service.listAll();
    }


    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listando a caixa"),
            @ApiResponse(responseCode = "404", description = "Não foi possivel encontrar dados"),
    })
    public ResponseEntity<CaixaListagemDTO> listByID(@PathVariable Integer id) {
        return service.listByID(id);
    }


    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Caixa cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Atributo(s) inválido(s)"),
    })
    public ResponseEntity<CaixaListagemDTO> create(
            @RequestBody @Valid CaixaCriacaoDTO novaCaixa
    ){
        Caixa caixa = CaixaMapper.toEntity(novaCaixa);
        Caixa caixaSalva = service.create(caixa, novaCaixa.getItensCaixa(), novaCaixa.getIdPedido());
        CaixaListagemDTO caixaDTO = CaixaMapper.toDTO(caixaSalva);
        return ResponseEntity.ok(caixaDTO);
    }


    @PutMapping("/{id}")
    public ResponseEntity<CaixaListagemDTO> update(
            @PathVariable Integer id,
            @RequestBody @Valid CaixaUpdateDTO caixaAtualixada
    ) {
        return service.update(id, caixaAtualixada);
    }

}
