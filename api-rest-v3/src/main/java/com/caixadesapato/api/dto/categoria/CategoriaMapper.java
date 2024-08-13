package com.caixadesapato.api.dto.categoria;

import com.caixadesapato.api.model.Categoria;

import java.util.List;

public class CategoriaMapper {

    public static Categoria toEntity(CategoriaCriacaoDTO categoriaCriacaoDTO){
        return Categoria.builder()
                .nome(categoriaCriacaoDTO.getNome())
                .qtdeProdutos(categoriaCriacaoDTO.getQtdeProdutos())
                .estagio(categoriaCriacaoDTO.getEstagio())
                .condicao(1)
                .build();
    }

    public static CategoriaListagemDTO toDTO(Categoria categoria){
        return CategoriaListagemDTO.builder()
                .id(categoria.getId())
                .nome(categoria.getNome())
                .qtdeProdutos(categoria.getQtdeProdutos())
                .estagio(categoria.getEstagio())
                .condicao(categoria.getCondicao())
                .build();
    }

    public static List<CategoriaListagemDTO> toDTO(List<Categoria> categorias){
        return categorias.stream()
                .map(CategoriaMapper::toDTO)
                .toList();
    }

}
