package com.example.apirestv2.api.controller;

import com.example.apirestv2.domain.produto.Produto;
import com.example.apirestv2.service.produto.ProdutoService;
import com.example.apirestv2.service.produto.dto.*;
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listando os produtos"),
            @ApiResponse(responseCode = "204", description = "Nenhuma produto cadastrado"),
    })
    public ResponseEntity<List<ProdutoListagemDTO>> listAll(){
        return service.listAll();
    }


    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listando o produto"),
            @ApiResponse(responseCode = "404", description = "Não foi possivel encontrar dados"),
    })
    public ResponseEntity<ProdutoListagemDTO> listById(
            @PathVariable Integer id
    ) {
        Produto produtoPorID = service.listById(id);
        ProdutoListagemDTO produtoDTO = ProdutoMapper.toDTO(produtoPorID);
        return ResponseEntity.ok(produtoDTO);
    }


    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Atributo(s) inválido(s)"),
    })
    public ResponseEntity<ProdutoListagemDTO> create(
            @RequestBody @Valid ProdutoCriacaoDTO novoProduto
    ) {
        return service.create(novoProduto);
    }


    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Atributo(s) inválido(s)"),
            @ApiResponse(responseCode = "404", description = "Não foi possivel encontrar dados"),
    })
    public ResponseEntity<ProdutoListagemDTO> update(
            @PathVariable Integer id,
            @RequestBody @Valid ProdutoAtualizacaoDTO novosDados
    ) {
        return service.update(id, novosDados);
    }


    @PatchMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Nome e preço do produto atualizados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Atributo(s) inválido(s)"),
            @ApiResponse(responseCode = "404", description = "Não foi possivel encontrar dados"),
    })
    public ResponseEntity<ProdutoListagemDTO> updateNameAndPrice(
            @PathVariable Integer id,
            @RequestBody @Valid ProdutoPatchDTO novosDados
    ) {
        return service.updateNameAndPrice(id, novosDados);
    }


    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Não foi possivel encontrar dados"),
    })
    public ResponseEntity<Void> disableItem(@PathVariable int id){
        return service.disableItem(id);
    }

}
