package com.example.apirestv2.service.produto.dto;

import com.example.apirestv2.domain.produto.Produto;
import java.util.List;

public class ProdutoMapper {

    public static Produto toEntity(ProdutoCriacaoDTO dto){

        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setCategoriaProduto(dto.getCategoriaProduto());
        produto.setFaixaEtaria(dto.getFaixaEtaria());
        produto.setValor(dto.getValor());
        produto.setGenero(dto.getGenero());

        return produto;

    }

    public static ProdutoListagemDTO toDTO(Produto produto){

        ProdutoListagemDTO dto = new ProdutoListagemDTO();

        dto.setId(produto.getId());
        dto.setNome(produto.getNome());
        dto.setCategoriaProduto(produto.getCategoriaProduto());
        dto.setValor(produto.getValor());
        dto.setFaixaEtaria(produto.getFaixaEtaria());
        dto.setGenero(produto.getGenero());

        return dto;

    }

    public static List<ProdutoListagemDTO> toListDTO(List<Produto> produtos){
        return produtos.stream()
                        .map(ProdutoMapper::toDTO)
                        .toList();
    }

}
