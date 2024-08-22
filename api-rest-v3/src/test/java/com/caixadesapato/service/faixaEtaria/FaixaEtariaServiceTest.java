package com.caixadesapato.service.faixaEtaria;

import com.caixadesapato.api.dto.faixaEtaria.FaixaEtariaUpdateDTO;
import com.caixadesapato.api.model.FaixaEtaria;
import com.caixadesapato.api.repository.FaixaEtariaRepository;
import com.caixadesapato.api.service.FaixaEtariaService;
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

class FaixaEtariaServiceTest {

    @Mock
    private FaixaEtariaRepository faixaEtariaRepository;

    @InjectMocks
    private FaixaEtariaService faixaEtariaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListAll() {
        List<FaixaEtaria> faixasEtarias = List.of(new FaixaEtaria(), new FaixaEtaria());

        when(faixaEtariaRepository.findAll()).thenReturn(faixasEtarias);

        List<FaixaEtaria> result = faixaEtariaService.listAll();

        assertEquals(faixasEtarias, result);
        verify(faixaEtariaRepository).findAll();
    }

    @Test
    void testListByParams() {
        Integer condicao = 1;
        List<FaixaEtaria> faixasEtarias = List.of(new FaixaEtaria());

        when(faixaEtariaRepository.findByCondicaoEquals(condicao)).thenReturn(faixasEtarias);

        List<FaixaEtaria> result = faixaEtariaService.listByParams(condicao);

        assertEquals(faixasEtarias, result);
        verify(faixaEtariaRepository).findByCondicaoEquals(condicao);
    }

    @Test
    void testFindByIdSuccess() {
        FaixaEtaria faixaEtaria = new FaixaEtaria();
        faixaEtaria.setId(1);

        when(faixaEtariaRepository.findById(1)).thenReturn(Optional.of(faixaEtaria));

        FaixaEtaria result = faixaEtariaService.findById(1);

        assertNotNull(result);
        assertEquals(faixaEtaria, result);
        verify(faixaEtariaRepository).findById(1);
    }

    @Test
    void testFindByIdNotFound() {
        when(faixaEtariaRepository.findById(anyInt())).thenReturn(Optional.empty());

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> faixaEtariaService.findById(1)
        );

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals("Não encontrado", thrown.getReason());
        verify(faixaEtariaRepository).findById(1);
    }

    @Test
    void testCreate() {
        FaixaEtaria novaFaixaEtaria = new FaixaEtaria();
        when(faixaEtariaRepository.save(novaFaixaEtaria)).thenReturn(novaFaixaEtaria);

        FaixaEtaria result = faixaEtariaService.create(novaFaixaEtaria);

        assertNotNull(result);
        assertEquals(novaFaixaEtaria, result);
        verify(faixaEtariaRepository).save(novaFaixaEtaria);
    }

    @Test
    void testUpdateSuccess() {
        Integer id = 1;
        FaixaEtaria faixaEtariaExistente = new FaixaEtaria();
        FaixaEtariaUpdateDTO updateDTO = new FaixaEtariaUpdateDTO();
        updateDTO.setFaixaNome("Nome Atualizado");

        when(faixaEtariaRepository.findById(id)).thenReturn(Optional.of(faixaEtariaExistente));
        when(faixaEtariaRepository.save(faixaEtariaExistente)).thenReturn(faixaEtariaExistente);

        FaixaEtaria result = faixaEtariaService.update(id, updateDTO);

        assertNotNull(result);
        assertEquals("Nome Atualizado", result.getFaixaNome());
        verify(faixaEtariaRepository).findById(id);
        verify(faixaEtariaRepository).save(faixaEtariaExistente);
    }

    @Test
    void testUpdateNotFound() {
        Integer id = 1;
        FaixaEtariaUpdateDTO updateDTO = new FaixaEtariaUpdateDTO();

        when(faixaEtariaRepository.findById(id)).thenReturn(Optional.empty());

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> faixaEtariaService.update(id, updateDTO)
        );

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals("Não encontrado", thrown.getReason());
        verify(faixaEtariaRepository).findById(id);
        verify(faixaEtariaRepository, never()).save(any(FaixaEtaria.class));
    }

    @Test
    void testChangeStatusSuccess() {
        Integer id = 1;
        Integer novoStatus = 2;
        FaixaEtaria faixaEtaria = new FaixaEtaria();
        faixaEtaria.setId(id);
        faixaEtaria.setCondicao(1);

        when(faixaEtariaRepository.findById(id)).thenReturn(Optional.of(faixaEtaria));
        when(faixaEtariaRepository.save(faixaEtaria)).thenReturn(faixaEtaria);

        FaixaEtaria result = faixaEtariaService.changeStatus(id, novoStatus);

        assertNotNull(result);
        assertEquals(novoStatus, result.getCondicao());
        verify(faixaEtariaRepository).findById(id);
        verify(faixaEtariaRepository).save(faixaEtaria);
    }

    @Test
    void testChangeStatusNotFound() {
        Integer id = 1;
        Integer novoStatus = 2;

        when(faixaEtariaRepository.findById(id)).thenReturn(Optional.empty());

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> faixaEtariaService.changeStatus(id, novoStatus)
        );

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals("Não encontrado", thrown.getReason());
        verify(faixaEtariaRepository).findById(id);
        verify(faixaEtariaRepository, never()).save(any(FaixaEtaria.class));
    }
}
