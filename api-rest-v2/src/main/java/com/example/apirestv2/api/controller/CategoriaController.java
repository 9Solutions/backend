
package com.example.apirestv2.api.controller;

import com.example.apirestv2.domain.categoria.Categoria;
import com.example.apirestv2.service.categoria.CategoriaService;
import com.example.apirestv2.service.categoria.dto.CategoriaCriacaoDTO;
import com.example.apirestv2.service.categoria.dto.CategoriaListagemDTO;
import com.example.apirestv2.service.categoria.dto.CategoriaMapper;
import com.example.apirestv2.service.categoria.dto.CategoriaUpdateDTO;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@AllArgsConstructor
public class CategoriaController {
    @Autowired
    private CategoriaService service;

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listando as categorias"),
            @ApiResponse(responseCode = "204", description = "Nenhuma categoria cadastrada"),
    })
    public ResponseEntity<List<CategoriaListagemDTO>> listAll (){
        List<Categoria> categorias = service.listAll();

        if(categorias.isEmpty()){ return ResponseEntity.noContent().build(); }

        List<CategoriaListagemDTO> categoriaDto = CategoriaMapper.toDTO(categorias);
        return ResponseEntity.ok().body(categoriaDto);
    }

    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listando a categoria"),
            @ApiResponse(responseCode = "404", description = "Não foi possivel encontrar dados"),
    })
    public ResponseEntity<CategoriaListagemDTO> listById (@PathVariable Integer id){
        Categoria categoria = service.findById(id);
        CategoriaListagemDTO categoriaDto = CategoriaMapper.toDTO(categoria);
        return ResponseEntity.ok(categoriaDto);
    }

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Atributo(s) inválido(s)"),
    })
    public ResponseEntity<CategoriaListagemDTO> create(@Valid @RequestBody CategoriaCriacaoDTO categoriaCriacaoDTO){
        Categoria categoria = CategoriaMapper.toEntity(categoriaCriacaoDTO);
        Categoria categoriaSalva = service.create(categoria);
        CategoriaListagemDTO categoriaDto = CategoriaMapper.toDTO(categoriaSalva);
        return ResponseEntity.status(201).body(categoriaDto);
    }

    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoria atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Atributo(s) inválido(s)"),
            @ApiResponse(responseCode = "404", description = "Não foi possivel encontrar dados"),
    })
    public ResponseEntity<CategoriaListagemDTO> update(
            @Valid @RequestBody CategoriaUpdateDTO categoriaNova,
            @PathVariable Integer id
    ){
        Categoria categoriaAtualizada = service.update(id, categoriaNova);
        CategoriaListagemDTO categoriaDto = CategoriaMapper.toDTO(categoriaAtualizada);
        return ResponseEntity.status(204).body(categoriaDto);
    }

    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoria excluida com sucesso"),
            @ApiResponse(responseCode = "400", description = "Atributo(s) inválido(s)"),
            @ApiResponse(responseCode = "404", description = "Não foi possivel encontrar dados"),
    })
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.status(204).build();
    }
}

