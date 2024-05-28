package com.example.apirestv2.service.produto;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.apirestv2.domain.categoria.Categoria;
import com.example.apirestv2.domain.categoria.repository.CategoriaRepository;
import com.example.apirestv2.domain.faixaEtaria.FaixaEtaria;
import com.example.apirestv2.domain.faixaEtaria.repository.FaixaEtariaRepository;
import com.example.apirestv2.domain.produto.Produto;
import com.example.apirestv2.domain.produto.repository.ProdutoRepository;
import com.example.apirestv2.enums.EnumGenero;
import com.example.apirestv2.service.produto.dto.ProdutoAtualizacaoDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceUpdateTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private FaixaEtariaRepository faixaEtariaRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @Test
    @DisplayName("Deve atualizar um produto com dados válidos")
    void testAtualizaProdutoComDadosValidos() {
        Integer id = 1;
        Produto produto = new Produto();
        produto.setId(id);

        ProdutoAtualizacaoDTO novosDados = new ProdutoAtualizacaoDTO();
        novosDados.setNome("Nome Atualizado");
        novosDados.setValor(100.0);
        novosDados.setFaixaEtaria(1);
        novosDados.setCategoriaProduto(1);
        novosDados.setGenero(EnumGenero.F);

        FaixaEtaria faixaEtaria = new FaixaEtaria();
        faixaEtaria.setId(1);

        Categoria categoria = new Categoria();
        categoria.setId(1);

        when(produtoRepository.findById(id)).thenReturn(Optional.of(produto));
        when(faixaEtariaRepository.findById(1)).thenReturn(Optional.of(faixaEtaria));
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoria));
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        Produto result = produtoService.update(id, novosDados);

        assertNotNull(result);
        assertEquals("Nome Atualizado", result.getNome());
        assertEquals(100.0, result.getValor());
        assertEquals(faixaEtaria, result.getFaixaEtaria());
        assertEquals(categoria, result.getCategoriaProduto());
        assertEquals(EnumGenero.F, result.getGenero());
        verify(produtoRepository, times(1)).findById(id);
        verify(faixaEtariaRepository, times(1)).findById(1);
        verify(categoriaRepository, times(1)).findById(1);
        verify(produtoRepository, times(1)).save(produto);
    }

    @Test
    @DisplayName("Deve lançar exceção quando faixa etária ou categoria não encontrada")
    void testLancaExcecaoQuandoNaoEncontraFaixaEtariaOuCategoria() {
        Produto produto = new Produto();
        produto.setId(1);
        when(produtoRepository.findById(1)).thenReturn(Optional.of(produto));
        when(faixaEtariaRepository.findById(1)).thenReturn(Optional.empty());

        ProdutoAtualizacaoDTO novosDados = new ProdutoAtualizacaoDTO();
        novosDados.setFaixaEtaria(1);
        novosDados.setCategoriaProduto(1);

        assertThrows(ResponseStatusException.class, () -> {
            produtoService.update(1, novosDados);
        });

        verify(produtoRepository, never()).save(produto);
    }

    @Test
    @DisplayName("Deve retornar null quando produto não encontrado")
    void testRetornaNuloQuandoNaoEncontraProduto() {
        when(produtoRepository.findById(1)).thenReturn(Optional.empty());

        ProdutoAtualizacaoDTO novosDados = new ProdutoAtualizacaoDTO();
        novosDados.setFaixaEtaria(1);
        novosDados.setCategoriaProduto(1);

        Produto result = produtoService.update(1, novosDados);

        assertNull(result);
        verify(produtoRepository, never()).save(any(Produto.class));
    }
}
