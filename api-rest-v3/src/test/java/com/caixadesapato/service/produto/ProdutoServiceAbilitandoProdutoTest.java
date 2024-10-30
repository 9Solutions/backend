package com.caixadesapato.service.produto;

import com.caixadesapato.api.model.Produto;
import com.caixadesapato.api.repository.ProdutoRepository;
import com.caixadesapato.api.service.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProdutoServiceAbilitandoProdutoTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /*@Test
    void testChangeConditionToEnabledSuccess() {
        Produto produto = new Produto();
        produto.setCondicao(0); // Produto inicialmente desabilitado

        when(produtoRepository.findById(1)).thenReturn(Optional.of(produto));
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        Produto result = produtoService.changeCondition(1, 1); // Habilitando o produto

        assertEquals(1, result.getCondicao());
        verify(produtoRepository).save(produto);
    }*/

    @Test
    void testChangeConditionNotFound() {
        when(produtoRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> produtoService.changeCondition(1, 1));
    }
}
