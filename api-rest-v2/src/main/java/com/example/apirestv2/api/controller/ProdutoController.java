package com.example.apirestv2.api.controller;

import com.example.apirestv2.domain.produto.Produto;
import com.example.apirestv2.service.produto.ProdutoService;
import com.example.apirestv2.service.produto.dto.ProdutoAtualizacaoDTO;
import com.example.apirestv2.service.produto.dto.ProdutoCriacaoDTO;
import com.example.apirestv2.service.produto.dto.ProdutoListagemDTO;
import com.example.apirestv2.service.produto.dto.ProdutoPatchDTO;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @GetMapping
    public ResponseEntity<List<ProdutoListagemDTO>> listAll(){
        return service.listAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoListagemDTO> listById(
            @PathVariable Integer id
    ) {
        return service.listById(id);
    }

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cria o produto"),
            @ApiResponse(responseCode = "400", description = "Atributo(s) inválido(s)"),
    })
    public ResponseEntity<ProdutoListagemDTO> create(
            @RequestBody @Valid ProdutoCriacaoDTO novoProduto
    ) {
        return service.create(novoProduto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoListagemDTO> update(
            @PathVariable Integer id,
            @RequestBody @Valid ProdutoAtualizacaoDTO novosDados
    ) {
        return service.update(id, novosDados);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProdutoListagemDTO> updateNameAndPrice(
            @PathVariable Integer id,
            @RequestBody @Valid ProdutoPatchDTO novosDados
    ) {
        return service.updateNameAndPrice(id, novosDados);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> disableItem(@PathVariable int id){
        return service.disableItem(id);
    }

}
