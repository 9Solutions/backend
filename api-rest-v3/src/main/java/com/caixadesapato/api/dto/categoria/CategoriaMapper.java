package com.caixadesapato.api.dto.categoria;

import com.caixadesapato.api.model.Categoria;

import java.util.List;

public class CategoriaMapper {

    public static Categoria toEntity(CategoriaCriacaoDTO categoriaCriacaoDTO){
        Categoria categoria = new Categoria();
        categoria.setNome(categoriaCriacaoDTO.getNome());
        return categoria;
    }

    public static Categoria toEntity(CategoriaUpdateDTO categoriaUpdateDTO){
        Categoria categoria = new Categoria();
        categoria.setNome(categoriaUpdateDTO.getNome());
        return categoria;
    }

    public static CategoriaListagemDTO toDTO(Categoria categoria){
        return CategoriaListagemDTO.builder()
                .id(categoria.getId())
                .nome(categoria.getNome())
                .build();
    }

    public static List<CategoriaListagemDTO> toDTO(List<Categoria> categorias){
        return categorias.stream()
                .map(CategoriaMapper::toDTO)
                .toList();
    }

}
