package com.example.apirestv2.service.produto;

import com.example.apirestv2.domain.produto.Produto;
import com.example.apirestv2.domain.produto.repository.ProdutoRepository;
import com.example.apirestv2.service.produto.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository action;

    /* LISTA TODOS OS PRODUTOS */
    public ResponseEntity<List<ProdutoListagemDTO>> listAll(){
        List<Produto> produtos = action.findByAtivoIs(1);
        if(!produtos.isEmpty()){

            List<ProdutoListagemDTO> produtosDTO = ProdutoMapper.toListDTO(produtos);
            return ResponseEntity.status(200).body(produtosDTO);

        }
        return ResponseEntity.status(204).build();
    }

    /* LISTA UM PRODUTO POR ID */
    public Produto listById(Integer id){
        Optional<Produto> produto = action.findById(id);
        if(produto.isPresent()){
            return produto.get();
        }
        throw new IllegalArgumentException("Não existe");
    }

    /* CRIA UM PRODUTO NO BANCO DE DADOS */
    public ResponseEntity<ProdutoListagemDTO> create(
            ProdutoCriacaoDTO novoProduto
    ){
        if(!Objects.isNull(novoProduto)){
            Produto produto = ProdutoMapper.toEntity(novoProduto);
            ProdutoListagemDTO dto = ProdutoMapper.toDTO(action.save(produto));

            return ResponseEntity.status(201).body(dto);
        }
        return ResponseEntity.status(400).build();
    }

    /* ATUALIZA UM PRODUTO NO BANCO - Todos Atributos */
    public ResponseEntity<ProdutoListagemDTO> update(
            Integer id,
            ProdutoAtualizacaoDTO novosDados
    ) {
        Optional<Produto> produto = action.findById(id);
        if(produto.isPresent()){

            Produto atualizandoProduto = produto.get();
            atualizandoProduto.setNome(novosDados.getNome());
            atualizandoProduto.setCategoriaProduto(novosDados.getCategoriaProduto());
            atualizandoProduto.setValor(novosDados.getValor());
            atualizandoProduto.setFaixaEtaria(novosDados.getFaixaEtaria());
            atualizandoProduto.setGenero(novosDados.getGenero());

            ProdutoListagemDTO produtoDTO = ProdutoMapper.toDTO(action.save(atualizandoProduto));
            return ResponseEntity.status(201).body(produtoDTO);

        }
        return ResponseEntity.status(404).build();
    }

    /* ATUALIZA UM PRODUTO NO BANCO - Apenas NOME e VALOR*/
    public ResponseEntity<ProdutoListagemDTO> updateNameAndPrice(
            Integer id,
            ProdutoPatchDTO novosDados
    ){
        Optional<Produto> produto = action.findById(id);
        if(produto.isPresent()){

            Produto atualizacaoProduto = produto.get();
            atualizacaoProduto.setNome(novosDados.getNome());
            atualizacaoProduto.setValor(novosDados.getValor());

            ProdutoListagemDTO dto = ProdutoMapper.toDTO(action.save(atualizacaoProduto));
            return ResponseEntity.status(201).body(dto);

        }
        return ResponseEntity.status(404).build();
    }

    public ResponseEntity<Void> disableItem(int id){
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
