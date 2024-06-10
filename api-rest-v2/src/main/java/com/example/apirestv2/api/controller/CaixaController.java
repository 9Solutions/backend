package com.example.apirestv2.api.controller;

import com.example.apirestv2.domain.caixa.Caixa;
import com.example.apirestv2.domain.itemCaixa.ItemCaixa;
import com.example.apirestv2.service.caixa.CaixaService;
import com.example.apirestv2.service.caixa.dto.CaixaCriacaoDTO;
import com.example.apirestv2.service.caixa.dto.CaixaListagemDTO;
import com.example.apirestv2.service.caixa.dto.CaixaMapper;
import com.example.apirestv2.service.caixa.dto.CaixaUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Listar caixas ", description = "Listar todas caixas", tags = "Caixas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listar caixas"),
            @ApiResponse(responseCode = "204", description = "Lista vazia"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping
    public ResponseEntity<List<CaixaListagemDTO>> listAll(){
        List<Caixa> caixas = service.listAll();
        if(caixas.isEmpty()) return ResponseEntity.noContent().build();

        List<CaixaListagemDTO> caixasDTO = CaixaMapper.toDTO(caixas);
        return ResponseEntity.ok(caixasDTO);
    }


    @Operation(summary = "Listar dados de uma caixa ", description = "Listar dados de uma caixa pelo ID", tags = "Caixas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listar dados da caixa"),
            @ApiResponse(responseCode = "404", description = "Caixa não encontrada"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CaixaListagemDTO> listByID(
            @PathVariable Integer id
    ) {
        Caixa caixaPorID = service.listByID(id);
        CaixaListagemDTO caixaDTO = CaixaMapper.toDTO(caixaPorID);
        return ResponseEntity.ok(caixaDTO);
    }


    @Operation(summary = "Cadastrar uma caixa ", description = "Método responsável por cadastrar uma nova caixa", tags = "Caixas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Caixa cadastrada"),
            @ApiResponse(responseCode = "400", description = "Atributo inválido"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping
    public ResponseEntity<CaixaListagemDTO> create(
            @RequestBody @Valid CaixaCriacaoDTO novaCaixa
    ){
        Caixa caixa = CaixaMapper.toEntity(novaCaixa);
        service.insertQueue(caixa, novaCaixa.getItensCaixa(), novaCaixa.getIdPedido());
        return ResponseEntity.created(null).build();
    }


    @Operation(summary = "Atualizar uma caixa ", description = "Método responsável por atualizar dados de uma caixa", tags = "Caixas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Caixa atualizada"),
            @ApiResponse(responseCode = "400", description = "Atributo inválido"),
            @ApiResponse(responseCode = "404", description = "Caixa não encontrada"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CaixaListagemDTO> update(
            @PathVariable Integer id,
            @RequestBody @Valid CaixaUpdateDTO novosDados
    ) {
        Caixa caixaAtualizada = service.update(id, novosDados);
        CaixaListagemDTO caixaDTO = CaixaMapper.toDTO(caixaAtualizada);
        return ResponseEntity.ok(caixaDTO);
    }

    @PatchMapping
    public ResponseEntity<Void> statusChange(
            @RequestParam Integer idCaixa,
            @RequestParam Integer status
    ) {
        service.statusChange(idCaixa, status);
        return ResponseEntity.ok(null);
    }

}
