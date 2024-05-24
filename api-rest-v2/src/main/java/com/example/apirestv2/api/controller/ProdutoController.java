package com.example.apirestv2.api.controller;

import com.example.apirestv2.domain.categoria.Categoria;
import com.example.apirestv2.domain.categoria.repository.CategoriaRepository;
import com.example.apirestv2.domain.faixaEtaria.FaixaEtaria;
import com.example.apirestv2.domain.faixaEtaria.repository.FaixaEtariaRepository;
import com.example.apirestv2.domain.produto.Produto;
import com.example.apirestv2.service.categoria.CategoriaService;
import com.example.apirestv2.service.faixaEtaria.FaixaEtariaService;
import com.example.apirestv2.service.produto.ProdutoService;
import com.example.apirestv2.service.produto.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@RequestMapping("/produtos")
@AllArgsConstructor
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private FaixaEtariaService faixaEtariaService;

    @Operation(summary = "Listar produtos", description = "Listar todos os produtos", tags = "Produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos"),
            @ApiResponse(responseCode = "204", description = "Lista vazia"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @GetMapping
    public ResponseEntity<List<ProdutoListagemDTO>> listAll(){
        List<Produto> produtos = service.listAll();

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
