package com.caixadesapato.api.dto.produto;

import com.caixadesapato.api.model.Categoria;
import com.caixadesapato.api.model.FaixaEtaria;
import com.caixadesapato.api.model.Produto;

import java.util.List;

public class ProdutoMapper {

    public static Produto toEntity(ProdutoCriacaoDTO dto) {
        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setValor(dto.getValor());
        produto.setGenero(dto.getGenero());
        produto.setUrlImagem(dto.getUrlImagem());
        produto.setCondicao(1);
        produto.setCategoriaProduto(null);
        produto.setFaixaEtaria(null);
        return produto;
    }

    public static ProdutoListagemDTO.CategoriaProdutoDTO toCategoriaProdutoDTO(Categoria categoria) {
        ProdutoListagemDTO.CategoriaProdutoDTO dto = new ProdutoListagemDTO.CategoriaProdutoDTO();
        dto.setId(categoria.getId());
        dto.setNome(categoria.getNome());
        dto.setCondicao(categoria.getCondicao());
        return dto;
    }

    public static ProdutoListagemDTO.FaixaEtariaDTO toFaixaEtariaDTO(FaixaEtaria faixaEtaria) {
        ProdutoListagemDTO.FaixaEtariaDTO dto = new ProdutoListagemDTO.FaixaEtariaDTO();
        dto.setId(faixaEtaria.getId());
        dto.setFaixaNome(faixaEtaria.getFaixaNome());
        dto.setLimiteInferior(faixaEtaria.getLimiteInferior());
        dto.setLimiteSuperior(faixaEtaria.getLimiteSuperior());
        dto.setCondicao(faixaEtaria.getCondicao());
        return dto;
    }

    public static ProdutoListagemDTO toDTO(Produto produto) {
        ProdutoListagemDTO dto = new ProdutoListagemDTO();
        dto.setId(produto.getId());

        ProdutoListagemDTO.CategoriaProdutoDTO categoriaProdutoDTO = new ProdutoListagemDTO.CategoriaProdutoDTO();
        categoriaProdutoDTO.setId(produto.getCategoriaProduto().getId());
        categoriaProdutoDTO.setNome(produto.getCategoriaProduto().getNome());
        categoriaProdutoDTO.setCondicao(produto.getCategoriaProduto().getCondicao());

        ProdutoListagemDTO.FaixaEtariaDTO faixaEtariaDTO = new ProdutoListagemDTO.FaixaEtariaDTO();
        faixaEtariaDTO.setId(produto.getFaixaEtaria().getId());
        faixaEtariaDTO.setFaixaNome(produto.getFaixaEtaria().getFaixaNome());
        faixaEtariaDTO.setLimiteInferior(produto.getFaixaEtaria().getLimiteInferior());
        faixaEtariaDTO.setLimiteSuperior(produto.getFaixaEtaria().getLimiteSuperior());
        faixaEtariaDTO.setCondicao(produto.getFaixaEtaria().getCondicao());

        dto.setNome(produto.getNome());
        dto.setUrlImagem(produto.getUrlImagem());
        dto.setCategoria(toCategoriaProdutoDTO(produto.getCategoriaProduto()));
        dto.setValor(produto.getValor());
        dto.setFaixaEtaria(toFaixaEtariaDTO(produto.getFaixaEtaria()));
        dto.setGenero(produto.getGenero());
        dto.setCondicao(produto.getCondicao());
        dto.setCategoria(categoriaProdutoDTO);
        dto.setFaixaEtaria(faixaEtariaDTO);

        return dto;
    }


    public static List<ProdutoListagemDTO> toListDTO(List<Produto> produtos) {
        return produtos.stream()
                .map(ProdutoMapper::toDTO)
                .toList();
    }


}
