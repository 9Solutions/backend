package com.example.apirestv2.service.produto.dto;

import com.example.apirestv2.domain.categoria.Categoria;
import com.example.apirestv2.domain.categoria.repository.CategoriaRepository;
import com.example.apirestv2.domain.faixaEtaria.FaixaEtaria;
import com.example.apirestv2.domain.faixaEtaria.repository.FaixaEtariaRepository;
import com.example.apirestv2.domain.produto.Produto;
import com.example.apirestv2.service.categoria.dto.CategoriaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public class ProdutoMapper {
    public static Produto toEntity(ProdutoCriacaoDTO dto, Categoria categoriaProduto, FaixaEtaria faixaEtaria) {
        Produto produto = new Produto();

        produto.setNome(dto.getNome());
        produto.setCategoriaProduto(categoriaProduto);
        produto.setFaixaEtaria(faixaEtaria);
        produto.setValor(dto.getValor());
        produto.setGenero(dto.getGenero());
        produto.setAtivo(1);
        return produto;
    }

    public static ProdutoListagemDTO.CategoriaProdutoDTO toCategoriaProdutoDTO(Categoria categoria) {
        ProdutoListagemDTO.CategoriaProdutoDTO dto = new ProdutoListagemDTO.CategoriaProdutoDTO();

        dto.setId(categoria.getId());
        dto.setNome(categoria.getNome());
        return dto;
    }

    public static ProdutoListagemDTO.FaixaEtariaDTO toFaixaEtariaDTO(FaixaEtaria faixaEtaria) {
        ProdutoListagemDTO.FaixaEtariaDTO dto = new ProdutoListagemDTO.FaixaEtariaDTO();

        dto.setId(faixaEtaria.getId());
        dto.setFaixaNome(faixaEtaria.getFaixaNome());
        dto.setLimiteInferior(faixaEtaria.getLimiteInferior());
        dto.setLimiteSuperior(faixaEtaria.getLimiteSuperior());
        return dto;
    }

    public static ProdutoListagemDTO toDTO(Produto produto) {
        ProdutoListagemDTO dto = new ProdutoListagemDTO();
        dto.setId(produto.getId());

        ProdutoListagemDTO.CategoriaProdutoDTO categoriaProdutoDTO = new ProdutoListagemDTO.CategoriaProdutoDTO();
        categoriaProdutoDTO.setId(produto.getCategoriaProduto().getId());
        categoriaProdutoDTO.setNome(produto.getCategoriaProduto().getNome());

        ProdutoListagemDTO.FaixaEtariaDTO faixaEtariaDTO = new ProdutoListagemDTO.FaixaEtariaDTO();
        faixaEtariaDTO.setId(produto.getFaixaEtaria().getId());
        faixaEtariaDTO.setFaixaNome(produto.getFaixaEtaria().getFaixaNome());
        faixaEtariaDTO.setLimiteInferior(produto.getFaixaEtaria().getLimiteInferior());
        faixaEtariaDTO.setLimiteSuperior(produto.getFaixaEtaria().getLimiteSuperior());

        dto.setNome(produto.getNome());
        dto.setCategoria(toCategoriaProdutoDTO(produto.getCategoriaProduto()));
        dto.setValor(produto.getValor());
        dto.setFaixaEtaria(toFaixaEtariaDTO(produto.getFaixaEtaria()));
        dto.setGenero(produto.getGenero());
        dto.setAtivo(produto.getAtivo());
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
