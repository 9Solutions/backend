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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProdutoServiceChangeConditionTest {
    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /*@Test
    void testChangeConditionSuccess() {
        // Dados de teste
        Integer id = 1;
        Integer newCondition = 2;
        Produto produtoExistente = new Produto();
        produtoExistente.setCondicao(1); // Valor inicial da condição

        // Configuração dos mocks
        when(produtoRepository.findById(id)).thenReturn(Optional.of(produtoExistente));
        when(produtoRepository.save(produtoExistente)).thenReturn(produtoExistente);

        // Chamada do método
        Produto result = produtoService.changeCondition(id, newCondition);

        // Verificações
        assertNotNull(result);
        assertEquals(newCondition, result.getCondicao()); // Verifica se a condição foi alterada
        verify(produtoRepository).findById(id);
        verify(produtoRepository).save(produtoExistente);
    }*/

    @Test
    void testChangeConditionNotFound() {
        // Dados de teste
        Integer id = 1;
        Integer newCondition = 2;

        // Configuração dos mocks
        when(produtoRepository.findById(id)).thenReturn(Optional.empty());

        // Chamada do método e verificação da exceção
        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> produtoService.changeCondition(id, newCondition)
        );

        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals("Produto não encontrado", thrown.getReason());
        verify(produtoRepository).findById(id);
        verify(produtoRepository, never()).save(any(Produto.class));
    }
}
