package com.caixadesapato.service.produto;

import com.caixadesapato.api.dto.produto.ProdutoAtualizacaoDTO;
import com.caixadesapato.api.dto.produto.ProdutoPatchDTO;
import com.caixadesapato.api.model.Categoria;
import com.caixadesapato.api.model.FaixaEtaria;
import com.caixadesapato.api.model.Produto;
import com.caixadesapato.api.repository.ProdutoRepository;
import com.caixadesapato.api.service.ProdutoService;
import com.caixadesapato.api.service.CategoriaService;
import com.caixadesapato.api.service.FaixaEtariaService;
import com.caixadesapato.api.utils.enums.Genero;
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

public class ProdutoServiceUpdateTest {
    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private FaixaEtariaService faixaEtariaService;

    @Mock
    private CategoriaService categoriaService;

    @InjectMocks
    private ProdutoService produtoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateSuccess() {
        // Dados de teste
        Integer id = 1;
        Produto produtoExistente = new Produto();
        ProdutoAtualizacaoDTO novosDados = new ProdutoAtualizacaoDTO();
        novosDados.setNome("Nome Atualizado");
        novosDados.setValor(100.0);
        novosDados.setGenero(Genero.M);
        novosDados.setUrlImagem("http://imagem.com");
        novosDados.setIdFaixaEtaria(2);
        novosDados.setIdCategoriaProduto(3);

        FaixaEtaria faixaEtaria = new FaixaEtaria();
        Categoria categoria = new Categoria();

        // Configuração dos mocks
        when(produtoRepository.findById(id)).thenReturn(Optional.of(produtoExistente));
        when(faixaEtariaService.findById(novosDados.getIdFaixaEtaria())).thenReturn(faixaEtaria);
        when(categoriaService.findById(novosDados.getIdCategoriaProduto())).thenReturn(categoria);
        when(produtoRepository.save(produtoExistente)).thenReturn(produtoExistente);

        // Chamada do método
        /*Produto result = produtoService.update(id, novosDados);

        // Verificações
        assertNotNull(result);
        assertEquals(novosDados.getNome(), result.getNome());
        assertEquals(novosDados.getValor(), result.getValor());
        assertEquals(novosDados.getGenero(), result.getGenero());
        assertEquals(novosDados.getUrlImagem(), result.getUrlImagem());
        assertEquals(faixaEtaria, result.getFaixaEtaria());
        assertEquals(categoria, result.getCategoriaProduto());
        verify(produtoRepository).save(produtoExistente);
        verify(produtoRepository).findById(id);
        verify(faixaEtariaService).findById(novosDados.getIdFaixaEtaria());
        verify(categoriaService).findById(novosDados.getIdCategoriaProduto());*/
    }

    @Test
    void testUpdateNotFound() {
        // Dados de teste
        Integer id = 1;
        ProdutoAtualizacaoDTO novosDados = new ProdutoAtualizacaoDTO();

        // Configuração dos mocks
        when(produtoRepository.findById(id)).thenReturn(Optional.empty());

        // Chamada do método e verificação da exceção
        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> produtoService.update(id, novosDados)
        );

        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals("Produto não encontrado", thrown.getReason());
        verify(produtoRepository).findById(id);
        verify(produtoRepository, never()).save(any(Produto.class));
    }

    /*@Test
    void testUpdateNameAndPriceSuccess() {
        // Dados de teste
        Integer id = 1;
        Produto produtoExistente = new Produto();
        ProdutoPatchDTO novosDados = new ProdutoPatchDTO();
        novosDados.setNome("Nome Atualizado");
        novosDados.setValor(100.0);

        // Configuração dos mocks
        when(produtoRepository.findById(id)).thenReturn(Optional.of(produtoExistente));
        when(produtoRepository.save(produtoExistente)).thenReturn(produtoExistente);

        // Chamada do método
        Produto result = produtoService.updateNameAndPrice(id, novosDados);

        // Verificações
        assertNotNull(result);
        assertEquals(novosDados.getNome(), result.getNome());
        assertEquals(novosDados.getValor(), result.getValor());
        verify(produtoRepository).save(produtoExistente);
        verify(produtoRepository).findById(id);
    }*/

    @Test
    void testUpdateNameAndPriceNotFound() {
        // Dados de teste
        Integer id = 1;
        ProdutoPatchDTO novosDados = new ProdutoPatchDTO();

        // Configuração dos mocks
        when(produtoRepository.findById(id)).thenReturn(Optional.empty());

        // Chamada do método e verificação da exceção
        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> produtoService.updateNameAndPrice(id, novosDados)
        );

        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals("Produto não encontrado", thrown.getReason());
        verify(produtoRepository).findById(id);
        verify(produtoRepository, never()).save(any(Produto.class));
    }
}
