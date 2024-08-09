package com.caixadesapato.api.controller;

import com.caixadesapato.api.dto.categoria.CategoriaCriacaoDTO;
import com.caixadesapato.api.dto.categoria.CategoriaListagemDTO;
import com.caixadesapato.api.dto.categoria.CategoriaMapper;
import com.caixadesapato.api.dto.categoria.CategoriaUpdateDTO;
import com.caixadesapato.api.model.Categoria;
import com.caixadesapato.api.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@AllArgsConstructor
public class CategoriaController {

    private final CategoriaService service;

    @Operation(summary = "Listar categorias ", description = "Listar todas as categorias", tags = "Categorias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de categorias"),
            @ApiResponse(responseCode = "204", description = "Lista vazia"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping
    public ResponseEntity<List<CategoriaListagemDTO>> listAll (){
        List<Categoria> categorias = service.listAll();
        if(categorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<CategoriaListagemDTO> categoriaDto = CategoriaMapper.toDTO(categorias);
        return ResponseEntity.ok().body(categoriaDto);
    }


    @Operation(summary = "Listar categorias por filtros", description = "Listar categorias com parâmetros de consulta", tags = "Categorias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de categorias"),
            @ApiResponse(responseCode = "204", description = "Lista vazia"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/filter")
    public ResponseEntity<List<CategoriaListagemDTO>> listByParams(
            @RequestParam(required = false) Integer estagio,
            @RequestParam Integer condicao
    ){
        List<Categoria> categorias = service.findByParams(estagio, condicao);
        if(categorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<CategoriaListagemDTO> categoriaDto = CategoriaMapper.toDTO(categorias);
        return ResponseEntity.ok().body(categoriaDto);
    }


    @Operation(summary = "Listar dados de 1(uma) categoria ", description = "Listar dados de uma categoria pelo ID", tags = "Categorias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listar dados da categoria"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaListagemDTO> listById (
            @PathVariable Integer id
    ) {
        Categoria categoria = service.findById(id);
        CategoriaListagemDTO categoriaDto = CategoriaMapper.toDTO(categoria);
        return ResponseEntity.ok(categoriaDto);
    }


    @Operation(summary = "Cadastrar uma nova categoria ", description = "Método responsável por cadastrar uma nova categoria", tags = "Categorias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria cadastrada"),
            @ApiResponse(responseCode = "400", description = "Atributo inválido"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping
    public ResponseEntity<CategoriaListagemDTO> create(
            @Valid @RequestBody CategoriaCriacaoDTO categoriaCriacaoDTO
    ) {
        Categoria categoria = CategoriaMapper.toEntity(categoriaCriacaoDTO);
        Categoria categoriaSalva = service.create(categoria);
        CategoriaListagemDTO categoriaDto = CategoriaMapper.toDTO(categoriaSalva);
        return ResponseEntity.status(201).body(categoriaDto);
    }


    @Operation(summary = "Atualizar dados de uma categoria ", description = "Método responsável por atualizar dados de uma categoria", tags = "Categorias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoria atualizada"),
            @ApiResponse(responseCode = "400", description = "Atributo inválido"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaListagemDTO> update(
            @Valid @RequestBody CategoriaUpdateDTO categoriaNova,
            @PathVariable Integer id
    ){
        Categoria categoriaAtualizada = service.update(id, categoriaNova);
        CategoriaListagemDTO categoriaDto = CategoriaMapper.toDTO(categoriaAtualizada);
        return ResponseEntity.ok(categoriaDto);
    }


    @Operation(summary = "Ativar ou desativar uma categoria", description = "Método responsável por ativar/desativar uma categoria", tags = "Categorias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Status alterado"),
            @ApiResponse(responseCode = "400", description = "Atributo inválido"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<CategoriaListagemDTO> changeStatus(
            @PathVariable Integer id,
            @RequestParam Integer condicao
    ){
        Categoria categoriaStatusAtualizado = service.changeStatus(id, condicao);
        return ResponseEntity.status(200).body(
                CategoriaMapper.toDTO(categoriaStatusAtualizado)
        );
    }

}
