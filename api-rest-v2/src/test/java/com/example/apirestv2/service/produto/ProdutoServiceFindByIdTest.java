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
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceFindByIdTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @Test
    @DisplayName("Deve encontrar um produto pelo id")
    void testRetornaProdutoPorId() {
        Produto produto = new Produto();
        produto.setId(1);
        when(produtoRepository.findById(1)).thenReturn(Optional.of(produto));

        Produto result = produtoService.findById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(produtoRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Deve lançar exceção quando produto não encontrado")
    void testRetornaExcecaoQuandoNaoEncontraProduto() {
        when(produtoRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            produtoService.findById(1);
        });

        verify(produtoRepository, times(1)).findById(1);
    }
}
