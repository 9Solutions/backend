package com.caixadesapato.service.produto;

import com.caixadesapato.api.model.Categoria;
import com.caixadesapato.api.model.FaixaEtaria;
import com.caixadesapato.api.model.Produto;
import com.caixadesapato.api.repository.ProdutoRepository;
import com.caixadesapato.api.service.CategoriaService;
import com.caixadesapato.api.service.FaixaEtariaService;
import com.caixadesapato.api.service.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProdutoServiceCreateTest {

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
    void testCreateSuccess() {
        Produto novoProduto = new Produto();
        Categoria categoria = new Categoria();
        FaixaEtaria faixaEtaria = new FaixaEtaria();

        when(categoriaService.findById(1)).thenReturn(categoria);
        when(faixaEtariaService.findById(1)).thenReturn(faixaEtaria);
        when(produtoRepository.save(any(Produto.class))).thenReturn(novoProduto);

        Produto result = produtoService.create(novoProduto, 1, 1);

        assertEquals(categoria, result.getCategoriaProduto());
        assertEquals(faixaEtaria, result.getFaixaEtaria());
        verify(produtoRepository).save(novoProduto);
    }

    @Test
    void testCreateCategoryNotFound() {
        Produto novoProduto = new Produto();

        when(categoriaService.findById(1)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada"));
        when(faixaEtariaService.findById(1)).thenReturn(new FaixaEtaria());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> produtoService.create(novoProduto, 1, 1));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Categoria não encontrada", exception.getReason());
    }

    @Test
    void testCreateFaixaEtariaNotFound() {
        Produto novoProduto = new Produto();

        when(categoriaService.findById(1)).thenReturn(new Categoria());
        when(faixaEtariaService.findById(1)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Faixa Etária não encontrada"));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> produtoService.create(novoProduto, 1, 1));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Faixa Etária não encontrada", exception.getReason());
    }



}
