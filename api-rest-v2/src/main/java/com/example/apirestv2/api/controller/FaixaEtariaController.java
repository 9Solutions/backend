package com.example.apirestv2.api.controller;

import com.example.apirestv2.domain.faixaEtaria.FaixaEtaria;
import com.example.apirestv2.service.faixaEtaria.FaixaEtariaService;
import com.example.apirestv2.service.faixaEtaria.dto.FaixaEtariaCriacaoDTO;
import com.example.apirestv2.service.faixaEtaria.dto.FaixaEtariaListagemDTO;
import com.example.apirestv2.service.faixaEtaria.dto.FaixaEtariaMapper;
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
    public ResponseEntity<List<FaixaEtariaListagemDTO>> listAll(){
        List<FaixaEtaria> faixaEtarias = service.listAll();

        if(faixaEtarias.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        List<FaixaEtariaListagemDTO> faixaEtariaListagemDTO = FaixaEtariaMapper.toDTO(faixaEtarias);
        return ResponseEntity.ok().body(faixaEtariaListagemDTO);
    }

    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listando a faixa etaria"),
            @ApiResponse(responseCode = "404", description = "Não foi possivel encontrar dados"),
    })
    public ResponseEntity<FaixaEtariaListagemDTO> listById(@PathVariable Integer id){
        FaixaEtaria faixaEtaria = service.findById(id);
        FaixaEtariaListagemDTO faixaEtariaListagemDTO = FaixaEtariaMapper.toDTO(faixaEtaria);
        return ResponseEntity.ok(faixaEtariaListagemDTO);
    }

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Faixa etaria cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Atributo(s) inválido(s)"),
    })
    public ResponseEntity<FaixaEtariaListagemDTO> create(@Valid @RequestBody FaixaEtariaCriacaoDTO faixaEtariaCriacaoDTO){
        FaixaEtaria faixaEtaria = FaixaEtariaMapper.toEntity(faixaEtariaCriacaoDTO);
        FaixaEtaria faixaEtariaSalva = service.create(faixaEtaria);
        FaixaEtariaListagemDTO faixaEtariaListagemDTO = FaixaEtariaMapper.toDTO(faixaEtariaSalva);
        return ResponseEntity.status(201).body(faixaEtariaListagemDTO);
    }

    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Faixa etaria atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Atributo(s) inválido(s)"),
            @ApiResponse(responseCode = "404", description = "Não foi possivel encontrar dados"),
    })
    public ResponseEntity<FaixaEtariaListagemDTO> update(
            @Valid @RequestBody FaixaEtariaUpdateDTO faixaEtariaUpdateDTO,
            @PathVariable Integer id
    ){
        FaixaEtaria faixaEtariaAtualizada = service.update(id, faixaEtariaUpdateDTO);
        FaixaEtariaListagemDTO faixaEtariaListagemDTO = FaixaEtariaMapper.toDTO(faixaEtariaAtualizada);
        return ResponseEntity.ok(faixaEtariaListagemDTO);
    }

    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Faixa etaria excluida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Não foi possivel encontrar dados"),
    })
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
