package com.example.apirestv2.api.controller;

import com.example.apirestv2.domain.faixaEtaria.FaixaEtaria;
import com.example.apirestv2.service.faixaEtaria.FaixaEtariaService;
import com.example.apirestv2.service.faixaEtaria.dto.FaixaEtariaCriacaoDTO;
import com.example.apirestv2.service.faixaEtaria.dto.FaixaEtariaListagemDTO;
import com.example.apirestv2.service.faixaEtaria.dto.FaixaEtariaMapper;
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
    public ResponseEntity<List<FaixaEtariaListagemDTO>> listAll(){
        List<FaixaEtaria> faixaEtarias = service.listAll();

        if(faixaEtarias.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        List<FaixaEtariaListagemDTO> faixaEtariaListagemDTO = FaixaEtariaMapper.toDTO(faixaEtarias);
        return ResponseEntity.ok().body(faixaEtariaListagemDTO);
    }


    @Operation(summary = "Listar faixas-etária por ID", description = "Listar uam faixas etária pelo ID", tags = "Faixa Etária")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listar dados da faixa etaria"),
            @ApiResponse(responseCode = "404", description = "Faixa Etária não encontrada"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<FaixaEtariaListagemDTO> listById(@PathVariable Integer id){
        FaixaEtaria faixaEtaria = service.findById(id);
        FaixaEtariaListagemDTO faixaEtariaListagemDTO = FaixaEtariaMapper.toDTO(faixaEtaria);
        return ResponseEntity.ok(faixaEtariaListagemDTO);

    }


    @Operation(summary = "Criar faixa-etária", description = "Método para criar uma nova fiaxa etária", tags = "Faixa Etária")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Faixa etaria cadastrada"),
            @ApiResponse(responseCode = "400", description = "Atributo inválido"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping
    public ResponseEntity<FaixaEtariaListagemDTO> create(@Valid @RequestBody FaixaEtariaCriacaoDTO faixaEtariaCriacaoDTO){
        FaixaEtaria faixaEtaria = FaixaEtariaMapper.toEntity(faixaEtariaCriacaoDTO);
        FaixaEtaria faixaEtariaSalva = service.create(faixaEtaria);
        FaixaEtariaListagemDTO faixaEtariaListagemDTO = FaixaEtariaMapper.toDTO(faixaEtariaSalva);
        return ResponseEntity.status(201).body(faixaEtariaListagemDTO);
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
            @PathVariable Integer id
    ){
        FaixaEtaria faixaEtariaAtualizada = service.update(id, faixaEtariaUpdateDTO);
        FaixaEtariaListagemDTO faixaEtariaListagemDTO = FaixaEtariaMapper.toDTO(faixaEtariaAtualizada);
        return ResponseEntity.ok(faixaEtariaListagemDTO);
    }


    @Operation(summary = "Deletar uma faixa etária", description = "Método para deletar uma fiaxa etária", tags = "Faixa Etária")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Faixa etaria excluida"),
            @ApiResponse(responseCode = "404", description = "Faixa Etária não encontrada"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
