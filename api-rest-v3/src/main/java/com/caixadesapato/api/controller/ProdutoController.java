package com.caixadesapato.api.controller;

import com.caixadesapato.api.dto.produto.*;
import com.caixadesapato.api.model.Categoria;
import com.caixadesapato.api.model.FaixaEtaria;
import com.caixadesapato.api.model.Produto;
import com.caixadesapato.api.service.CategoriaService;
import com.caixadesapato.api.service.FaixaEtariaService;
import com.caixadesapato.api.service.ProdutoService;
import com.caixadesapato.api.utils.enums.Condition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService service;

    private final CategoriaService categoriaService;

    private final FaixaEtariaService faixaEtariaService;

    @Operation(summary = "Listar produtos", description = "Listar todos os produtos", tags = "Produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos"),
            @ApiResponse(responseCode = "204", description = "Lista vazia"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @GetMapping
    public ResponseEntity<List<ProdutoListagemDTO>> listAll(
            @RequestParam Integer status
    ){
        List<Produto> produtos = service.listAllByCondition(status);

        if(produtos.isEmpty()) return ResponseEntity.noContent().build();

        List<ProdutoListagemDTO> produtosDTO = ProdutoMapper.toListDTO(produtos);
        return ResponseEntity.ok().body(produtosDTO);
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
        Produto produto = service.findById(id);
        ProdutoListagemDTO produtoDTO = ProdutoMapper.toDTO(produto);
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
    public ResponseEntity<ProdutoListagemDTO> create( @Valid @RequestBody ProdutoCriacaoDTO produtoCriacaoDTO) {
        Categoria categoria = categoriaService.findById(produtoCriacaoDTO.getCategoriaProduto());

        FaixaEtaria faixaEtaria = faixaEtariaService.findById(produtoCriacaoDTO.getFaixaEtaria());

        Produto produto = ProdutoMapper.toEntity(produtoCriacaoDTO, categoria, faixaEtaria);
        Produto produtoSalvo = service.create(produto);
        ProdutoListagemDTO produtoDTO = ProdutoMapper.toDTO(produtoSalvo);
        return ResponseEntity.status(201).body(produtoDTO);
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
            @RequestBody @Valid ProdutoAtualizacaoDTO produtoNovo
    ) {
        Produto produtoAtualizado = service.update(id, produtoNovo);
        ProdutoListagemDTO produtoDTO = ProdutoMapper.toDTO(produtoAtualizado);
        return ResponseEntity.ok(produtoDTO);
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
        Produto produtoAtualizado = service.updateNameAndPrice(id, novosDados);
        ProdutoListagemDTO produtoDTO = ProdutoMapper.toDTO(produtoAtualizado);
        return ResponseEntity.ok(produtoDTO);
    }


    @Operation(summary = "Muda o estado do produto", description = "Método responsável por mudar o estado de um produto entre ativo/desativo", tags = "Produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto ativado/desativado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PatchMapping
    public ResponseEntity<ProdutoListagemDTO> changeCondition(
            @RequestParam int id,
            @RequestParam Condition status
    ){
        Produto produtoAtualizado = service.changeCondition(id, status);
        ProdutoListagemDTO produtoDTO = ProdutoMapper.toDTO(produtoAtualizado);
        return ResponseEntity.status(201).body(produtoDTO);
    }

}
