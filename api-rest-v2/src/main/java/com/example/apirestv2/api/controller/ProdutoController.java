package com.example.apirestv2.api.controller;

import com.example.apirestv2.domain.produto.Produto;
import com.example.apirestv2.service.produto.ProdutoService;
import com.example.apirestv2.service.produto.dto.*;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Listar produtos", description = "Listar todos os produtos", tags = "Produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos"),
            @ApiResponse(responseCode = "204", description = "Lista vazia"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @GetMapping
    public ResponseEntity<List<ProdutoListagemDTO>> listAll(){
        return service.listAll();
    }


    @Operation(summary = "Lista um produto", description = "Listar dados de um produto pelo ID", tags = "Produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listar dados do produto"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoListagemDTO> listById(
            @PathVariable Integer id
    ) {
        Produto produtoPorID = service.listById(id);
        ProdutoListagemDTO produtoDTO = ProdutoMapper.toDTO(produtoPorID);
        return ResponseEntity.ok(produtoDTO);
    }

    @Operation(summary = "Criar um novo produto", description = "Método responsável por criar um novo produto", tags = "Produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto cadastrado"),
            @ApiResponse(responseCode = "400", description = "Atributo inválido"),
            @ApiResponse(responseCode = "404", description = "Categoria ou Faixa Etária não encontrada"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @PostMapping
    public ResponseEntity<ProdutoListagemDTO> create(
            @RequestBody @Valid ProdutoCriacaoDTO novoProduto
    ) {
        return service.create(novoProduto);
    }


    @Operation(summary = "Atualizar dados", description = "Método responsável por atualizar dados de um produto", tags = "Produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto atualizado"),
            @ApiResponse(responseCode = "400", description = "Atributo inválido"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoListagemDTO> update(
            @PathVariable Integer id,
            @RequestBody @Valid ProdutoAtualizacaoDTO novosDados
    ) {
        return service.update(id, novosDados);
    }


    @Operation(summary = "Atualizar nome e valor", description = "Método responsável por atualizar o NOME e o VALOR de um produto", tags = "Produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Nome e preço do produto atualizados"),
            @ApiResponse(responseCode = "400", description = "Atributo inválido"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<ProdutoListagemDTO> updateNameAndPrice(
            @PathVariable Integer id,
            @RequestBody @Valid ProdutoPatchDTO novosDados
    ) {
        return service.updateNameAndPrice(id, novosDados);
    }

    @Operation(summary = "Deletar um produto", description = "Método responsável por deletar um produto", tags = "Produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto deletado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> disableItem(@PathVariable int id){
        return service.disableItem(id);
    }

}
