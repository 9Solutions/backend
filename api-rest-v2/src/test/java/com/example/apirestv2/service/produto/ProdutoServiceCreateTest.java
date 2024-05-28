package com.example.apirestv2.service.produto;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.apirestv2.domain.produto.Produto;
import com.example.apirestv2.domain.produto.repository.ProdutoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceCreateTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @Test
    @DisplayName("Deve criar um produto")
    void testCriaUmProduto() {
        Produto produto = new Produto();
        when(produtoRepository.save(produto)).thenReturn(produto);

        Produto result = produtoService.create(produto);

        assertNotNull(result);
        verify(produtoRepository, times(1)).save(produto);
    }
}
