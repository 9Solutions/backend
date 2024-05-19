package com.example.apirestv2.api.controller;

import com.example.apirestv2.service.categoria.CategoriaService;
import com.example.apirestv2.service.categoria.dto.CategoriaCriacaoDTO;
import com.example.apirestv2.service.categoria.dto.CategoriaListagemDTO;
import com.example.apirestv2.service.categoria.dto.CategoriaUpdateDTO;
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

    @Operation(summary = "Listar categorias ", description = "Listar todas categorias", tags = "Categorias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de categorias"),
            @ApiResponse(responseCode = "204", description = "Lista vazia"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping
    public ResponseEntity<List<CategoriaListagemDTO>> listar(){
        return service.listAll();
    }


    @Operation(summary = "Listar dados de uma categoria ", description = "Listar dados de uma categoria pelo ID", tags = "Categorias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listar dados da categoria"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaListagemDTO> buscarPId(@PathVariable int id){
        return service.findById(id);
    }


    @Operation(summary = "Cadastrar uma nova categoria ", description = "Método responsável por cadastrar uma nova categoria", tags = "Categorias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria cadastrada"),
            @ApiResponse(responseCode = "400", description = "Atributo inválido"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping
    public ResponseEntity<CategoriaListagemDTO> criar(@Valid @RequestBody CategoriaCriacaoDTO categoriaCriacaoDTO){
        return service.create(categoriaCriacaoDTO);
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
            @Valid @RequestBody CategoriaUpdateDTO categoriaUpdateDTO,
            @PathVariable int id
    ){
        return service.update(id, categoriaUpdateDTO);
    }


    @Operation(summary = "Deletar uma categoria ", description = "Método responsável por deletar uma categoria", tags = "Categorias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoria excluida"),
            @ApiResponse(responseCode = "400", description = "Atributo inválido"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        return service.delete(id);
    }

}
