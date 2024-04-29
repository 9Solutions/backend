package com.example.apirestv2.api.controller;

import com.example.apirestv2.service.faixaEtaria.FaixaEtariaService;
import com.example.apirestv2.service.faixaEtaria.dto.FaixaEtariaCriacaoDTO;
import com.example.apirestv2.service.faixaEtaria.dto.FaixaEtariaListagemDTO;
import com.example.apirestv2.service.faixaEtaria.dto.FaixaEtariaUpdateDTO;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faixa-etaria")
@AllArgsConstructor
public class FaixaEtariaController {
    private final FaixaEtariaService service;

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listando as faixas etarias"),
            @ApiResponse(responseCode = "204", description = "Nenhuma faixa etaria cadastrada"),
    })
    public ResponseEntity<List<FaixaEtariaListagemDTO>> listar(){
        return service.listAll();
    }

    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listando a faixa etaria"),
            @ApiResponse(responseCode = "404", description = "Não foi possivel encontrar dados"),
    })
    public ResponseEntity<FaixaEtariaListagemDTO> buscarPId(@PathVariable int id){
        return service.findById(id);
    }

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Faixa etaria cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Atributo(s) inválido(s)"),
    })
    public ResponseEntity<FaixaEtariaListagemDTO> criar(@Valid @RequestBody FaixaEtariaCriacaoDTO faixaEtariaCriacaoDTO){
        return service.create(faixaEtariaCriacaoDTO);
    }

    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Faixa etaria atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Atributo(s) inválido(s)"),
            @ApiResponse(responseCode = "404", description = "Não foi possivel encontrar dados"),
    })
    public ResponseEntity<FaixaEtariaListagemDTO> update(
            @Valid @RequestBody FaixaEtariaUpdateDTO faixaEtariaUpdateDTO,
            @PathVariable int id
    ){
        return service.update(id, faixaEtariaUpdateDTO);
    }

    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Faixa etaria excluida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Não foi possivel encontrar dados"),
    })
    public ResponseEntity<Void> delete(@PathVariable int id){
        return service.delete(id);
    }
}
