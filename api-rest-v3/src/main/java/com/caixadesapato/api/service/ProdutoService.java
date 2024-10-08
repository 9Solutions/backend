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
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository action;
    private final FaixaEtariaService faixaEtariaService;
    private final CategoriaService categoriaService;

    private ConcurrentHashMap<Integer, Produto> tabelaProdutos = new ConcurrentHashMap<>();

    public List<Produto> listAllByCondition(Integer status) {
        if (status != null) {
            return action.findByCondicaoEquals(status);
        }
        return action.findAll();
    }

    public Produto findById(Integer id) {
        Produto produto = tabelaProdutos.get(id);
        if (produto != null) {
            return produto;
        }
        return action.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado")
        );
    }

    public Produto create(Produto novoProduto, Integer idCategoria, Integer idFaixaEtaria) {
        Categoria categoria = categoriaService.findById(idCategoria);
        FaixaEtaria faixaEtaria = faixaEtariaService.findById(idFaixaEtaria);
        novoProduto.setCategoriaProduto(categoria);
        novoProduto.setFaixaEtaria(faixaEtaria);

        Produto produtoSalvo = action.save(novoProduto);
        tabelaProdutos.put(produtoSalvo.getId(), produtoSalvo);
        return produtoSalvo;
    }

    public Produto update(Integer id, ProdutoAtualizacaoDTO novosDados) {
        Optional<Produto> produtoOptional = action.findById(id);
        if (produtoOptional.isPresent()) {
            FaixaEtaria faixaEtaria = faixaEtariaService.findById(novosDados.getIdFaixaEtaria());
            Categoria categoria = categoriaService.findById(novosDados.getIdCategoriaProduto());

            Produto atualizandoProduto = produtoOptional.get();
            atualizandoProduto.setNome(novosDados.getNome());
            atualizandoProduto.setValor(novosDados.getValor());
            atualizandoProduto.setGenero(novosDados.getGenero());
            atualizandoProduto.setUrlImagem(novosDados.getUrlImagem());
            atualizandoProduto.setFaixaEtaria(faixaEtaria);
            atualizandoProduto.setCategoriaProduto(categoria);

            Produto produtoAtualizado = action.save(atualizandoProduto);
            tabelaProdutos.put(produtoAtualizado.getId(), produtoAtualizado);
            return produtoAtualizado;
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produto não encontrado");
    }

    public Produto updateNameAndPrice(Integer id, ProdutoPatchDTO novosDados) {
        Optional<Produto> produtoOptional = action.findById(id);
        if (produtoOptional.isPresent()) {
            Produto atualizacaoProduto = produtoOptional.get();
            atualizacaoProduto.setNome(novosDados.getNome());
            atualizacaoProduto.setValor(novosDados.getValor());

            Produto produtoAtualizado = action.save(atualizacaoProduto);
            tabelaProdutos.put(produtoAtualizado.getId(), produtoAtualizado);
            return produtoAtualizado;
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produto não encontrado");
    }

    public Produto changeCondition(Integer id, Integer condition) {
        Optional<Produto> produtoOptional = action.findById(id);
        if (produtoOptional.isPresent()) {
            Produto produto = produtoOptional.get();
            produto.setCondicao(condition);

            Produto produtoAtualizado = action.save(produto);
            tabelaProdutos.put(produto.getId(), produtoAtualizado);
            return produtoAtualizado;
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produto não encontrado");
    }

    public void removeProduto(Integer id) {
        tabelaProdutos.remove(id);
        action.deleteById(id);
    }
}
