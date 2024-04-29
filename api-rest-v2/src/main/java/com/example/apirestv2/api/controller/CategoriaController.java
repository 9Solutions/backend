package com.example.apirestv2.api.controller;

import com.example.apirestv2.service.categoria.CategoriaService;
import com.example.apirestv2.service.categoria.dto.CategoriaCriacaoDTO;
import com.example.apirestv2.service.categoria.dto.CategoriaListagemDTO;
import com.example.apirestv2.service.categoria.dto.CategoriaUpdateDTO;
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

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listando as categorias"),
            @ApiResponse(responseCode = "204", description = "Nenhuma categoria cadastrada"),
    })
    public ResponseEntity<List<CategoriaListagemDTO>> listar(){
        return service.listAll();
    }

    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listando a categoria"),
            @ApiResponse(responseCode = "404", description = "Não foi possivel encontrar dados"),
    })
    public ResponseEntity<CategoriaListagemDTO> buscarPId(@PathVariable int id){
        return service.findById(id);
    }

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Atributo(s) inválido(s)"),
    })
    public ResponseEntity<CategoriaListagemDTO> criar(@Valid @RequestBody CategoriaCriacaoDTO categoriaCriacaoDTO){
        return service.create(categoriaCriacaoDTO);
    }

    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoria atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Atributo(s) inválido(s)"),
            @ApiResponse(responseCode = "404", description = "Não foi possivel encontrar dados"),
    })
    public ResponseEntity<CategoriaListagemDTO> update(
            @Valid @RequestBody CategoriaUpdateDTO categoriaUpdateDTO,
            @PathVariable int id
    ){
        return service.update(id, categoriaUpdateDTO);
    }

    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoria excluida com sucesso"),
            @ApiResponse(responseCode = "400", description = "Atributo(s) inválido(s)"),
            @ApiResponse(responseCode = "404", description = "Não foi possivel encontrar dados"),
    })
    public ResponseEntity<Void> delete(@PathVariable int id){
        return service.delete(id);
    }
}
