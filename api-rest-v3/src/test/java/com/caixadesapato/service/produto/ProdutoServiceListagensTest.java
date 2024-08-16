package com.caixadesapato.service.produto;

import com.caixadesapato.api.model.Produto;
import com.caixadesapato.api.repository.ProdutoRepository;
import com.caixadesapato.api.service.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class ProdutoServiceListagensTest {
    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListAllByConditionWithStatus() {
        // Dados de teste
        Integer status = 1;
        Produto produto1 = new Produto();
        Produto produto2 = new Produto();
        List<Produto> produtos = List.of(produto1, produto2);

        // Comportamento esperado do mock
        when(produtoRepository.findByCondicaoEquals(status)).thenReturn(produtos);

        // Chamada do método
        List<Produto> result = produtoService.listAllByCondition(status);

        // Verificações
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(produtos, result);
        verify(produtoRepository).findByCondicaoEquals(status);
        verify(produtoRepository, never()).findAll();
    }

    @Test
    void testListAllByConditionWithoutStatus() {
        // Dados de teste
        Produto produto1 = new Produto();
        Produto produto2 = new Produto();
        List<Produto> produtos = List.of(produto1, produto2);

        // Comportamento esperado do mock
        when(produtoRepository.findAll()).thenReturn(produtos);

        // Chamada do método
        List<Produto> result = produtoService.listAllByCondition(null);

        // Verificações
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(produtos, result);
        verify(produtoRepository).findAll();
        verify(produtoRepository, never()).findByCondicaoEquals(anyInt());
    }

    @Test
    void testFindByIdSuccess() {
        // Dados de teste
        Integer id = 1;
        Produto produto = new Produto();

        // Comportamento esperado do mock
        when(produtoRepository.findById(id)).thenReturn(Optional.of(produto));

        // Chamada do método
        Produto result = produtoService.findById(id);

        // Verificações
        assertNotNull(result);
        assertEquals(produto, result);
        verify(produtoRepository).findById(id);
    }

    @Test
    void testFindByIdNotFound() {
        // Dados de teste
        Integer id = 1;

        // Comportamento esperado do mock
        when(produtoRepository.findById(id)).thenReturn(Optional.empty());

        // Chamada do método e verificação da exceção
        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> produtoService.findById(id)
        );

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals("Não encontrado", thrown.getReason());
        verify(produtoRepository).findById(id);
    }
}