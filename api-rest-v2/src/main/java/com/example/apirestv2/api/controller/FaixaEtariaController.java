package com.example.apirestv2.api.controller;

import com.example.apirestv2.service.faixaEtaria.FaixaEtariaService;
import com.example.apirestv2.service.faixaEtaria.dto.FaixaEtariaCriacaoDTO;
import com.example.apirestv2.service.faixaEtaria.dto.FaixaEtariaListagemDTO;
import com.example.apirestv2.service.faixaEtaria.dto.FaixaEtariaUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Listar faixas-etárias", description = "Listar faixas etárias", tags = "Faixa Etária")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de faixas etarias"),
            @ApiResponse(responseCode = "204", description = "Lista vazia"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping
    public ResponseEntity<List<FaixaEtariaListagemDTO>> listar(){
        return service.listAll();
    }


    @Operation(summary = "Listar faixas-etária por ID", description = "Listar uam faixas etária pelo ID", tags = "Faixa Etária")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listar dados da faixa etaria"),
            @ApiResponse(responseCode = "404", description = "Faixa Etária não encontrada"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<FaixaEtariaListagemDTO> buscarPId(@PathVariable int id){
        return service.findById(id);
    }


    @Operation(summary = "Criar faixa-etária", description = "Método para criar uma nova fiaxa etária", tags = "Faixa Etária")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Faixa etaria cadastrada"),
            @ApiResponse(responseCode = "400", description = "Atributo inválido"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping
    public ResponseEntity<FaixaEtariaListagemDTO> criar(@Valid @RequestBody FaixaEtariaCriacaoDTO faixaEtariaCriacaoDTO){
        return service.create(faixaEtariaCriacaoDTO);
    }


    @Operation(summary = "Atualizar dados da faixa-etária", description = "Método para atualizar dados de uma fiaxa etária", tags = "Faixa Etária")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Faixa etaria atualizada"),
            @ApiResponse(responseCode = "400", description = "Atributo inválido"),
            @ApiResponse(responseCode = "404", description = "Faixa Etária não encontrada"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<FaixaEtariaListagemDTO> update(
            @Valid @RequestBody FaixaEtariaUpdateDTO faixaEtariaUpdateDTO,
            @PathVariable int id
    ){
        return service.update(id, faixaEtariaUpdateDTO);
    }


    @Operation(summary = "Deletar uma faixa etária", description = "Método para deletar uma fiaxa etária", tags = "Faixa Etária")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Faixa etaria excluida"),
            @ApiResponse(responseCode = "404", description = "Faixa Etária não encontrada"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        return service.delete(id);
    }

}
