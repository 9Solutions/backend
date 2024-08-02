package com.caixadesapato.api.service;

import com.caixadesapato.api.dto.produto.ProdutoAtualizacaoDTO;
import com.caixadesapato.api.dto.produto.ProdutoPatchDTO;
import com.caixadesapato.api.model.Categoria;
import com.caixadesapato.api.model.FaixaEtaria;
import com.caixadesapato.api.model.Produto;
import com.caixadesapato.api.repository.CategoriaRepository;
import com.caixadesapato.api.repository.FaixaEtariaRepository;
import com.caixadesapato.api.repository.ProdutoRepository;
import com.caixadesapato.api.utils.enums.Condition;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository action;

    private final FaixaEtariaRepository faixaEtariaRepository;

    private final CategoriaRepository categoriaRepository;

    public List<Produto> listAllByCondition(Integer status) {
        return action.getByCondition(status);
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

        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Produto não encontrado"
        );

    }

    public Produto updateNameAndPrice(Integer id, ProdutoPatchDTO novosDados) {
        Optional<Produto> produtoOptional = action.findById(id);
        if (produtoOptional.isPresent()) {

            Produto atualizacaoProduto = produtoOptional.get();
            atualizacaoProduto.setNome(novosDados.getNome());
            atualizacaoProduto.setValor(novosDados.getValor());

            return action.save(atualizacaoProduto);
        }

        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Produto não encontrado"
        );

    }

    public Produto changeCondition (Integer id, Condition status){
        Optional<Produto> produto = action.findById(id);

        if (produto.isPresent()) {
            System.out.println(status.getCondition());
            produto.get().setAtivo(status.getCondition());
            return action.save(produto.get());
        }

        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Produto não encontrado"
        );

    }

}
