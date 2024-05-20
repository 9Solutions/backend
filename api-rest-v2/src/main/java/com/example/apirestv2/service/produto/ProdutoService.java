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

    private final ProdutoRepository action;
    private final FaixaEtariaRepository faixaEtariaRepository;
    private final CategoriaRepository categoriaRepository;

    /* LISTA TODOS OS PRODUTOS */
    public ResponseEntity<List<ProdutoListagemDTO>> listAll() {
        List<Produto> produtos = action.findByAtivoIs(1);
        if (!produtos.isEmpty()) {

            List<ProdutoListagemDTO> produtosDTO = ProdutoMapper.toListDTO(produtos);
            return ResponseEntity.status(200).body(produtosDTO);

        }
        return ResponseEntity.status(204).build();
    }

    /* LISTA UM PRODUTO POR ID */
    public Produto listById(Integer id) {
        Produto produto = action.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado")
        );
        return produto;
    }

    /* CRIA UM PRODUTO NO BANCO DE DADOS */
    public ResponseEntity<ProdutoListagemDTO> create(ProdutoCriacaoDTO novoProduto) {
        if (!Objects.isNull(novoProduto)) {
            Optional<FaixaEtaria> faixaEtaria = faixaEtariaRepository.findById(novoProduto.getFaixaEtaria());
            Optional<Categoria> categoria = categoriaRepository.findById(novoProduto.getCategoriaProduto());

            if (faixaEtaria.isEmpty() || categoria.isEmpty()) {
                return ResponseEntity.status(400).build();
            }

            Produto produto = ProdutoMapper.toEntity(novoProduto, categoria.get(), faixaEtaria.get());
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
        if (produto.isPresent()) {

            Optional<FaixaEtaria> faixaEtaria = faixaEtariaRepository.findById(novosDados.getFaixaEtaria());
            Optional<Categoria> categoria = categoriaRepository.findById(novosDados.getCategoriaProduto());

            if (faixaEtaria.isEmpty() || categoria.isEmpty()) {
                return ResponseEntity.status(400).build();
            }

            Produto atualizandoProduto = produto.get();
            atualizandoProduto.setNome(novosDados.getNome());
            atualizandoProduto.setCategoriaProduto(categoria.get());
            atualizandoProduto.setValor(novosDados.getValor());
            atualizandoProduto.setFaixaEtaria(faixaEtaria.get());
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
    ) {
        Optional<Produto> produto = action.findById(id);
        if (produto.isPresent()) {

            Produto atualizacaoProduto = produto.get();
            atualizacaoProduto.setNome(novosDados.getNome());
            atualizacaoProduto.setValor(novosDados.getValor());

            ProdutoListagemDTO dto = ProdutoMapper.toDTO(action.save(atualizacaoProduto));
            return ResponseEntity.status(201).body(dto);

        }
        return ResponseEntity.status(404).build();
    }

    public ResponseEntity<Void> disableItem(int id) {
        Optional<Produto> produto = action.findById(id);
        if (produto.isPresent()) {
            produto.get().setAtivo(0);
            action.save(produto.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Void> enableItem(int id) {
        Optional<Produto> produto = action.findById(id);
        if (produto.isPresent()) {
            produto.get().setAtivo(1);
            action.save(produto.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
