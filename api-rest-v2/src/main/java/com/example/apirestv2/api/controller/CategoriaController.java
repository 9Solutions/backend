
package com.example.apirestv2.api.controller;

import com.example.apirestv2.domain.categoria.Categoria;
import com.example.apirestv2.service.categoria.CategoriaService;
import com.example.apirestv2.service.categoria.dto.CategoriaCriacaoDTO;
import com.example.apirestv2.service.categoria.dto.CategoriaListagemDTO;
import com.example.apirestv2.service.categoria.dto.CategoriaMapper;
import com.example.apirestv2.service.categoria.dto.CategoriaUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
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
    private final CategoriaService service;

    @Operation(summary = "Listar categorias ", description = "Listar todas categorias", tags = "Categorias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de categorias"),
            @ApiResponse(responseCode = "204", description = "Lista vazia"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
  @GetMapping
  public ResponseEntity<List<CategoriaListagemDTO>> listAll (){
        List<Categoria> categorias = service.listAll();

        if(categorias.isEmpty()) return ResponseEntity.noContent().build();

        List<CategoriaListagemDTO> categoriaDto = CategoriaMapper.toDTO(categorias);
        return ResponseEntity.ok().body(categoriaDto);

    }


    @Operation(summary = "Listar dados de uma categoria ", description = "Listar dados de uma categoria pelo ID", tags = "Categorias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listar dados da categoria"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaListagemDTO> listById (@PathVariable Integer id){
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
    public ResponseEntity<CategoriaListagemDTO> create(@Valid @RequestBody CategoriaCriacaoDTO categoriaCriacaoDTO){
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


    @Operation(summary = "Deletar uma categoria ", description = "Método responsável por deletar uma categoria", tags = "Categorias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoria excluida"),
            @ApiResponse(responseCode = "400", description = "Atributo inválido"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.status(204).build();
    }

}

