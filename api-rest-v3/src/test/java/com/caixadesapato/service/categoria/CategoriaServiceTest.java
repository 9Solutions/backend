package com.caixadesapato.service.categoria;

import com.caixadesapato.api.dto.categoria.CategoriaUpdateDTO;
import com.caixadesapato.api.model.Categoria;
import com.caixadesapato.api.repository.CategoriaRepository;
import com.caixadesapato.api.service.CategoriaService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListAll() {
        List<Categoria> categorias = List.of(new Categoria(), new Categoria());

        when(categoriaRepository.findAll()).thenReturn(categorias);

        List<Categoria> result = categoriaService.listAll();

        assertEquals(categorias, result);
        verify(categoriaRepository).findAll();
    }

    @Test
    void testFindByIdSuccess() {
        Categoria categoria = new Categoria();
        categoria.setId(1);

        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoria));

        Categoria result = categoriaService.findById(1);

        assertNotNull(result);
        assertEquals(categoria, result);
        verify(categoriaRepository).findById(1);
    }

    @Test
    void testFindByIdNotFound() {
        when(categoriaRepository.findById(anyInt())).thenReturn(Optional.empty());

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> categoriaService.findById(1)
        );

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals("Categoria não encontrada", thrown.getReason());
        verify(categoriaRepository).findById(1);
    }

    @Test
    void testFindByParamsWithEstagio() {
        Integer estagio = 1;
        Integer condicao = 2;
        List<Categoria> categorias = List.of(new Categoria());

        when(categoriaRepository.findByEstagioEqualsAndCondicaoEquals(estagio, condicao)).thenReturn(categorias);

        List<Categoria> result = categoriaService.findByParams(estagio, condicao);

        assertEquals(categorias, result);
        verify(categoriaRepository).findByEstagioEqualsAndCondicaoEquals(estagio, condicao);
    }

    @Test
    void testFindByParamsWithoutEstagio() {
        Integer condicao = 2;
        List<Categoria> categorias = List.of(new Categoria());

        when(categoriaRepository.findByCondicaoEquals(condicao)).thenReturn(categorias);

        List<Categoria> result = categoriaService.findByParams(null, condicao);

        assertEquals(categorias, result);
        verify(categoriaRepository).findByCondicaoEquals(condicao);
    }

    @Test
    void testCreate() {
        Categoria novaCategoria = new Categoria();
        when(categoriaRepository.save(novaCategoria)).thenReturn(novaCategoria);

        Categoria result = categoriaService.create(novaCategoria);

        assertNotNull(result);
        assertEquals(novaCategoria, result);
        verify(categoriaRepository).save(novaCategoria);
    }

    @Test
    void testUpdateSuccess() {
        Integer id = 1;
        Categoria categoriaExistente = new Categoria();
        CategoriaUpdateDTO updateDTO = new CategoriaUpdateDTO();
        updateDTO.setNome("Nome Atualizado");
        updateDTO.setEstagio(2);
        updateDTO.setQtdeProdutos(10);

        when(categoriaRepository.findById(id)).thenReturn(Optional.of(categoriaExistente));
        when(categoriaRepository.save(categoriaExistente)).thenReturn(categoriaExistente);

        Categoria result = categoriaService.update(id, updateDTO);

        assertNotNull(result);
        assertEquals("Nome Atualizado", result.getNome());
        assertEquals(2, result.getEstagio());
        assertEquals(10, result.getQtdeProdutos());
        verify(categoriaRepository).findById(id);
        verify(categoriaRepository).save(categoriaExistente);
    }

    @Test
    void testUpdateNotFound() {
        Integer id = 1;
        CategoriaUpdateDTO updateDTO = new CategoriaUpdateDTO();

        when(categoriaRepository.findById(id)).thenReturn(Optional.empty());

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> categoriaService.update(id, updateDTO)
        );

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals("Categoria não encontrada", thrown.getReason());
        verify(categoriaRepository).findById(id);
        verify(categoriaRepository, never()).save(any(Categoria.class));
    }

    @Test
    void testChangeStatusSuccess() {
        Integer id = 1;
        Integer novoStatus = 3;
        Categoria categoria = new Categoria();
        categoria.setId(id);
        categoria.setCondicao(1);

        when(categoriaRepository.findById(id)).thenReturn(Optional.of(categoria));
        when(categoriaRepository.save(categoria)).thenReturn(categoria);

        Categoria result = categoriaService.changeStatus(id, novoStatus);

        assertNotNull(result);
        assertEquals(novoStatus, result.getCondicao());
        verify(categoriaRepository).findById(id);
        verify(categoriaRepository).save(categoria);
    }

    @Test
    void testChangeStatusNotFound() {
        Integer id = 1;
        Integer novoStatus = 3;

        when(categoriaRepository.findById(id)).thenReturn(Optional.empty());

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> categoriaService.changeStatus(id, novoStatus)
        );

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals("Categoria não encontrada", thrown.getReason());
        verify(categoriaRepository).findById(id);
        verify(categoriaRepository, never()).save(any(Categoria.class));
    }
}
