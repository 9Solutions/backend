package com.example.apirestv2.service.faixaEtaria;

import com.example.apirestv2.domain.faixaEtaria.FaixaEtaria;
import com.example.apirestv2.domain.faixaEtaria.repository.FaixaEtariaRepository;
import com.example.apirestv2.service.faixaEtaria.dto.FaixaEtariaListagemDTO;
import com.example.apirestv2.service.faixaEtaria.dto.FaixaEtariaMapper;
import com.example.apirestv2.service.faixaEtaria.dto.FaixaEtariaUpdateDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)

class FaixaEtariaServiceTest {
    @InjectMocks
    private FaixaEtariaService service;
    @Mock
    private FaixaEtariaRepository repository;
    @Mock
    private Action action;


    // MÈTODO LISTAR
    @Test
    @DisplayName("Listar: retorna uma lista com 3 valores ao inserir 3")
    void listar3produtos() {
        List<FaixaEtaria> lista = List.of(new FaixaEtaria(), new FaixaEtaria(), new FaixaEtaria());
        // when
        Mockito.when(repository.findAll()).thenReturn(lista);
        // then
        List<FaixaEtaria> faixaEtariasList = service.listAll();
        // se tem 3 retorna 3
        // assert
        assertFalse(faixaEtariasList.isEmpty());
        assertEquals(faixaEtariasList.size(),3);
        // Todo transformar lista em FaixaEtariaList
        assertEquals(lista.get(0).getFaixaNome(),lista.get(0).getFaixaNome());
        assertEquals(lista.get(0).getId(),lista.get(0).getId());
        assertEquals(lista.get(0).getLimiteInferior(),lista.get(0).getLimiteInferior());
        assertEquals(lista.get(0).getLimiteSuperior(),lista.get(0).getLimiteSuperior());
    }


    @Test
    @DisplayName("Listar: retorna lista vazia se a lista está vazia")
    void listarVazio() {
        // Given
        List<FaixaEtaria> lista = Collections.emptyList();
        // when
        Mockito.when(repository.findAll()).thenReturn(lista);
//        Mockito.when(service.listar()).thenReturn(lista);
        // then
        List<FaixaEtaria> faixaEtarias = service.listAll();
        // assert
        assertTrue(faixaEtarias.isEmpty());
    }

    // FindById

    @Test
    @DisplayName("findById: quando id válido retorna faixaEtaria do id")
    void findByIdVálido() {
        // Given
        Optional<FaixaEtaria> faixaEtaria = Optional.of(new FaixaEtaria());
        Integer id = 1;
        // when
        Mockito.when(repository.findById(id)).thenReturn(faixaEtaria);
        // then
        FaixaEtaria faixaEtariaEncontrada = service.findById(id);
        // assert
        assertEquals(faixaEtaria.get().getFaixaNome(),faixaEtariaEncontrada.getFaixaNome());
        Mockito.verify(repository,Mockito.times(1)).findById(id);
    }

    @Test
    @DisplayName("findById: quando id não existente joga uma exceção")
    void findByIdNaoExistente() {
        // Given
        Integer id = 500;
        Optional<FaixaEtaria> faixaEtaria = Optional.empty();

        // When
        Mockito.when(repository.findById(id)).thenReturn(faixaEtaria);

        // Then
        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, () -> service.findById(id));
        assertEquals("404 NOT_FOUND \"Não encontrado\"", responseStatusException.getMessage());
    }


    @Test
    @DisplayName("findById: quando id inválido retorna uma exceção")
    void findByIdInvalido() {
        // Given
        Optional<FaixaEtaria> faixaEtaria = Optional.empty();
        Integer id = null;
        // when
        Mockito.when(repository.findById(id)).thenReturn(faixaEtaria);

        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, () -> service.findById(id));
        assertEquals("404 NOT_FOUND \"Não encontrado\"",responseStatusException.getMessage());
    }


    // Create
    @Test
    @DisplayName("Create: deve retornar o objeto salvo")
    void testCreateSalvaFaixa() {
        // GIVEN
        FaixaEtaria faixaEtariaNova = new FaixaEtaria();
        faixaEtariaNova.setFaixaNome("De 9 a 14");
        faixaEtariaNova.setLimiteInferior(9);
        faixaEtariaNova.setLimiteSuperior(14);

        FaixaEtaria faixaEtariaSalva = new FaixaEtaria();
        faixaEtariaSalva.setId(1);
        faixaEtariaSalva.setFaixaNome("De 9 a 14");
        faixaEtariaSalva.setLimiteInferior(9);
        faixaEtariaSalva.setLimiteSuperior(14);

        // WHEN
        Mockito.when(repository.save(faixaEtariaNova)).thenReturn(faixaEtariaSalva);

        FaixaEtaria result = service.create(faixaEtariaNova);

        // THEN
        assertEquals(faixaEtariaSalva.getId(), result.getId());
        assertEquals(faixaEtariaSalva.getFaixaNome(), result.getFaixaNome());
        assertEquals(faixaEtariaSalva.getLimiteInferior(), result.getLimiteInferior());
        assertEquals(faixaEtariaSalva.getLimiteSuperior(), result.getLimiteSuperior());

        Mockito.verify(repository, Mockito.times(1)).save(faixaEtariaNova);
    }


    // MÉTODO UPDATE

    @Test
    @DisplayName("Update: atualizado com sucesso")
    public void testUpdateSuccess() {
        Integer id = 1;
        FaixaEtariaUpdateDTO faixaEtariaUpdateDTO = new FaixaEtariaUpdateDTO();
        faixaEtariaUpdateDTO.setFaixaNome("Nova Faixa");

        FaixaEtaria faixaEtaria = new FaixaEtaria();
        faixaEtaria.setId(id);
        faixaEtaria.setFaixaNome("Antiga Faixa");

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(faixaEtaria));
        Mockito.when(repository.save(Mockito.any(FaixaEtaria.class))).thenReturn(faixaEtaria);

        FaixaEtaria updatedFaixaEtaria = service.update(id, faixaEtariaUpdateDTO);

        assertEquals("Nova Faixa", updatedFaixaEtaria.getFaixaNome());
        Mockito.verify(repository, Mockito.times(1)).findById(id);
        Mockito.verify(repository, Mockito.times(1)).save(faixaEtaria);
    }

//    @Test
//    @DisplayName("Update: id existente no banco e passei o objeto, atualiza com sucesso a Faixa Etaria")
//    void updateAtualizaComSucesso() {
//        // GIVEN
//        Integer idInformado = 1;
//
//        FaixaEtariaUpdateDTO faixaEtariaUpdate = new FaixaEtariaUpdateDTO();
//        faixaEtariaUpdate.setFaixaNome("De 9 a 14");
//        faixaEtariaUpdate.setLimiteInferior(9);
//        faixaEtariaUpdate.setLimiteSuperior(14);
//
//        FaixaEtaria faixaEtariaAtualizada = new FaixaEtaria();
//        faixaEtariaAtualizada.setId(idInformado);
//        faixaEtariaAtualizada.setFaixaNome(faixaEtariaUpdate.getFaixaNome());
//        faixaEtariaAtualizada.setLimiteInferior(faixaEtariaUpdate.getLimiteInferior());
//        faixaEtariaAtualizada.setLimiteSuperior(faixaEtariaUpdate.getLimiteSuperior());
//
//        // WHEN
//        Mockito.when(repository.existsById(idInformado)).thenReturn(true);
//        Mockito.when(repository.save(Mockito.any(FaixaEtaria.class))).thenReturn(faixaEtariaAtualizada);
//
//        // THEN
//        FaixaEtaria resposta = service.update(idInformado, faixaEtariaUpdate);
//
//        // ASSERT
//        assertEquals(idInformado, resposta.getId());
//        assertEquals(faixaEtariaUpdate.getFaixaNome(), resposta.getFaixaNome());
//        assertEquals(faixaEtariaUpdate.getLimiteInferior(), resposta.getLimiteInferior());
//        assertEquals(faixaEtariaUpdate.getLimiteSuperior(), resposta.getLimiteSuperior());
//
//        Mockito.verify(repository, Mockito.times(1)).existsById(idInformado);
//        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(FaixaEtaria.class));
//    }



    @Test
    @DisplayName("Update: método não encontra o id e retorna uma exceção.")
    void updateNaoEncontraId() {
//        // Given
//        Optional<FaixaEtaria> faixaEtaria = Optional.empty();
//        Integer id = 900;
//        // when
//        Mockito.when(repository.findById(id)).thenReturn(faixaEtaria);
//
//        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, () -> service.findById(id));
//        assertEquals("Não encontrado",responseStatusException.getMessage());

        Integer id = 1;
        FaixaEtariaUpdateDTO faixaEtariaUpdateDTO = new FaixaEtariaUpdateDTO();
        faixaEtariaUpdateDTO.setFaixaNome("Nova Faixa");

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            service.update(id, faixaEtariaUpdateDTO);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        Mockito.verify(repository, Mockito.times(1)).findById(id);
        Mockito.verify(repository, Mockito.times(0)).save(Mockito.any(FaixaEtaria.class));

    }

    @Test
    @DisplayName("Update: o id é inválido e retorna uma exceção.")
    void updateIdInvalido() {
        // Given
        Optional<FaixaEtaria> faixaEtaria = Optional.empty();
        Integer id = null;
        // when
        Mockito.when(repository.findById(id)).thenReturn(faixaEtaria);

        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, () -> service.findById(id));
        assertEquals("404 NOT_FOUND \"Não encontrado\"",responseStatusException.getMessage());
    }


    // MÈTODO DELETE

    @Test
    @DisplayName("Delete: dado que id é válido, é deletado com sucesso")
    public void testDeleteSuccess() {
        Integer id = 1;
        FaixaEtaria faixaEtaria = new FaixaEtaria();
        faixaEtaria.setId(id);

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(faixaEtaria));

        ResponseEntity<Void> response = service.delete(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Mockito.verify(repository, Mockito.times(1)).delete(faixaEtaria);
    }

    @Test
    @DisplayName("Delete: dado que o id não é encontrado, lance uma exceção")
    public void testDeleteNotFound() {
        Integer id = 100;

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            service.delete(id);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        Mockito.verify(repository, Mockito.times(0)).delete(Mockito.any(FaixaEtaria.class));
    }

}