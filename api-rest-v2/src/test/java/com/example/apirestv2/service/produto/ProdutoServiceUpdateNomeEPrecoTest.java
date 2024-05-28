package com.example.apirestv2.service.produto;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.apirestv2.domain.produto.Produto;
import com.example.apirestv2.domain.produto.repository.ProdutoRepository;
import com.example.apirestv2.service.produto.dto.ProdutoPatchDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class ProdutoServiceUpdateNomeEPrecoTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve atualizar nome e preço de um produto")
    void testAtualizaNomeEPrecoDoProduto() {
        Integer id = 1;
        Produto produto = new Produto();
        produto.setId(id);
        ProdutoPatchDTO patchDTO = new ProdutoPatchDTO();
        patchDTO.setNome("Novo Nome");
        patchDTO.setValor(99.99);

        when(produtoRepository.findById(id)).thenReturn(Optional.of(produto));
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        Produto result = produtoService.updateNameAndPrice(id, patchDTO);

        assertNotNull(result);
        assertEquals("Novo Nome", result.getNome());
        assertEquals(99.99, result.getValor());
        verify(produtoRepository, times(1)).findById(id);
        verify(produtoRepository, times(1)).save(produto);
    }

    @Test
    @DisplayName("Deve retornar null quando produto não encontrado")
    void testRetornaNullQuandoProdutoNaoExiste() {
        when(produtoRepository.findById(1)).thenReturn(Optional.empty());

        ProdutoPatchDTO novosDados = new ProdutoPatchDTO();
        novosDados.setNome("Novo Nome");
        novosDados.setValor(200.0);

        Produto result = produtoService.updateNameAndPrice(1, novosDados);

        assertNull(result);
        verify(produtoRepository, never()).save(any(Produto.class));
    }
}