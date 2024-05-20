package com.example.apirestv2.api.controller;

import com.example.apirestv2.domain.produto.Produto;
import com.example.apirestv2.service.produto.ProdutoService;
import com.example.apirestv2.service.produto.dto.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/produtos")
@AllArgsConstructor
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listando os produtos"),
            @ApiResponse(responseCode = "204", description = "Nenhuma produto cadastrado"),
    })
    public ResponseEntity<List<ProdutoListagemDTO>> listAll(){
        List<Produto> produtos = service.listAll();

        if(produtos.isEmpty()) return ResponseEntity.noContent().build();

        List<ProdutoListagemDTO> produtosDTO = ProdutoMapper.toDTO(produtos);
        return ResponseEntity.ok().body(produtosDTO);
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
            @RequestBody @Valid ProdutoCriacaoDTO produtoCriacaoDTO
    ) {
        Produto produto = ProdutoMapper.toEntity(produtoCriacaoDTO);
        Produto produtoSalvo = service.create(produto);
        ProdutoListagemDTO produtoDTO = ProdutoMapper.toDTO(produtoSalvo);
        return ResponseEntity.status(201).body(produtoDTO);
    }


    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Atributo(s) inválido(s)"),
            @ApiResponse(responseCode = "404", description = "Não foi possivel encontrar dados"),
    })
    public ResponseEntity<ProdutoListagemDTO> update(
            @PathVariable Integer id,
            @RequestBody @Valid ProdutoAtualizacaoDTO produtoNovo
    ) {
        Produto produtoAtualizado = service.update(id, produtoNovo);
        ProdutoListagemDTO produtoDTO = ProdutoMapper.toDTO(produtoAtualizado);
        return ResponseEntity.ok(produtoDTO);
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
        Produto produtoAtualizado = service.updateNameAndPrice(id, novosDados);
        ProdutoListagemDTO produtoDTO = ProdutoMapper.toDTO(produtoAtualizado);
        return ResponseEntity.ok(produtoDTO);
    }


    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Não foi possivel encontrar dados"),
    })
    public ResponseEntity<Void> disableItem(@PathVariable Integer id){
        return service.disableItem(id);
    }

}
