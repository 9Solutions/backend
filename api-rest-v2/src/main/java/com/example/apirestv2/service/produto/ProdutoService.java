package com.example.apirestv2.service.produto;

import com.example.apirestv2.domain.categoria.Categoria;
import com.example.apirestv2.domain.produto.Produto;
import com.example.apirestv2.domain.produto.repository.ProdutoRepository;
import com.example.apirestv2.service.produto.dto.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProdutoService {

    @Autowired
    private ProdutoRepository action;

    /* LISTA TODOS OS PRODUTOS */
    public List<Produto> listAll(){ return action.findAll();}

    /* LISTA UM PRODUTO POR ID */
    public Produto listById(Integer id){
        return action.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado")
        );}

    /* CRIA UM PRODUTO NO BANCO DE DADOS */
    public Produto create(Produto novoProduto){
        return action.save(novoProduto);
    }

    /* ATUALIZA UM PRODUTO NO BANCO - Todos Atributos */
    public Produto update(Integer id, ProdutoAtualizacaoDTO produtoAtualizado) {
        Produto produto = action.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado")
        );

        produto.setNome(produtoAtualizado.getNome());
        produto.setGenero(produtoAtualizado.getGenero());
        produto.setValor(produtoAtualizado.getValor());
        produto.setCategoriaProduto(produtoAtualizado.getCategoriaProduto());
        produto.setFaixaEtaria(produtoAtualizado.getFaixaEtaria());

        return action.save(produto);
    }

    /* ATUALIZA UM PRODUTO NO BANCO - Apenas NOME e VALOR*/
    public Produto updateNameAndPrice(
            Integer id,
            ProdutoPatchDTO novosDados
    ){
        Produto produto = action.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado")
        );

        if(Objects.nonNull(novosDados.getNome())){
            produto.setNome(novosDados.getNome());
        }

        if(Objects.nonNull(novosDados.getValor())){
            produto.setValor(novosDados.getValor());
        }

        return action.save(produto);
    }

    public ResponseEntity<Void> disableItem(Integer id){
        Optional<Produto> produto = action.findById(id);
        if(produto.isPresent()){
            produto.get().setAtivo(0);
            action.save(produto.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Void> enableItem(int id){
        Optional<Produto> produto = action.findById(id);
        if(produto.isPresent()){
            produto.get().setAtivo(1);
            action.save(produto.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
