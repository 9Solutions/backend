package com.example.apirestv2.service.produto;

import com.example.apirestv2.domain.produto.Produto;
import com.example.apirestv2.domain.produto.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProdutoServiceListAllTest {
    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve listar todos os produtos")
    void testListaTodosOsProdutos() {
        Produto produto1 = new Produto();
        Produto produto2 = new Produto();
        List<Produto> produtos = Arrays.asList(produto1, produto2);
        Mockito.when(produtoRepository.findAll()).thenReturn(produtos);

        List<Produto> result = produtoService.listAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(produtoRepository, times(1)).findAll();
    }
}
