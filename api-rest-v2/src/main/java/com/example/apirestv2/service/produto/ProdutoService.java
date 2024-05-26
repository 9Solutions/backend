package com.example.apirestv2.service.produto;

import com.example.apirestv2.domain.categoria.Categoria;

import com.example.apirestv2.domain.categoria.repository.CategoriaRepository;
import com.example.apirestv2.domain.faixaEtaria.FaixaEtaria;
import com.example.apirestv2.domain.faixaEtaria.repository.FaixaEtariaRepository;

import com.example.apirestv2.domain.produto.Produto;
import com.example.apirestv2.domain.produto.repository.ProdutoRepository;
import com.example.apirestv2.service.categoria.CategoriaService;
import com.example.apirestv2.service.faixaEtaria.FaixaEtariaService;
import com.example.apirestv2.service.produto.dto.*;

import lombok.AllArgsConstructor;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    @Autowired
    private ProdutoRepository action;

    @Autowired
    private FaixaEtariaRepository faixaEtariaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Produto> listAll() {
        return action.findAll();
    }

    public Produto findById(Integer id) {
        return action.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado")
        );
    }

    public Produto create(Produto novoProduto) {
        return action.save(novoProduto);
    }

    public Produto update(Integer id, ProdutoAtualizacaoDTO novosDados) {
        Optional<Produto> produtoOptional = action.findById(id);

        if (produtoOptional.isPresent()) {

            Optional<FaixaEtaria> faixaEtaria = faixaEtariaRepository.findById(novosDados.getFaixaEtaria());
            Optional<Categoria> categoria = categoriaRepository.findById(novosDados.getCategoriaProduto());

            if (faixaEtaria.isEmpty() || categoria.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria ou faixa etária não encontrada");
            }

            Produto atualizandoProduto = produtoOptional.get();
            atualizandoProduto.setNome(novosDados.getNome());
            atualizandoProduto.setCategoriaProduto(categoria.get());
            atualizandoProduto.setValor(novosDados.getValor());
            atualizandoProduto.setFaixaEtaria(faixaEtaria.get());
            atualizandoProduto.setGenero(novosDados.getGenero());

            return action.save(atualizandoProduto);
        }
        return null;
    }


    public Produto updateNameAndPrice(Integer id, ProdutoPatchDTO novosDados) {
        Optional<Produto> produtoOptional = action.findById(id);
        if (produtoOptional.isPresent()) {

            Produto atualizacaoProduto = produtoOptional.get();
            atualizacaoProduto.setNome(novosDados.getNome());
            atualizacaoProduto.setValor(novosDados.getValor());

            return action.save(atualizacaoProduto);
        }
        return null;
    }


    public ResponseEntity<Void> disableItem (Integer id){
        Optional<Produto> produto = action.findById(id);

        if (produto.isPresent()) {
            produto.get().setAtivo(0);
            action.save(produto.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Void> enableItem ( int id){
        Optional<Produto> produto = action.findById(id);
        if (produto.isPresent()) {
            produto.get().setAtivo(1);
            action.save(produto.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
