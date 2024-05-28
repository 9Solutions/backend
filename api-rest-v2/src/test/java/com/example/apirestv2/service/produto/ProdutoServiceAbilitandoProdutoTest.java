package com.example.apirestv2.service.produto;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.apirestv2.domain.produto.Produto;
import com.example.apirestv2.domain.produto.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class ProdutoServiceAbilitandoProdutoTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve habilitar um produto")
    void testAbilitaUmProduto() {
        Produto produto = new Produto();
        produto.setId(1);
        when(produtoRepository.findById(1)).thenReturn(Optional.of(produto));

        ResponseEntity<Void> response = produtoService.enableItem(1);

        assertEquals(ResponseEntity.noContent().build(), response);
        assertEquals(1, produto.getAtivo());
        verify(produtoRepository, times(1)).save(produto);
    }

    @Test
    @DisplayName("Deve retornar not found quando produto não encontrado")
    void testRetornaNotFoundQuandoNaoEncontraProduto() {
        when(produtoRepository.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = produtoService.enableItem(1);

        assertEquals(ResponseEntity.notFound().build(), response);
        verify(produtoRepository, never()).save(any(Produto.class));
    }
}
