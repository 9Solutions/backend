package com.example.apirestv2.service.faixaEtaria;

import com.example.apirestv2.domain.faixaEtaria.FaixaEtaria;
import com.example.apirestv2.domain.faixaEtaria.repository.FaixaEtariaRepository;
import com.example.apirestv2.service.faixaEtaria.dto.FaixaEtariaListagemDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

    @Test
    @DisplayName("Método listar retorna uma lista com 3 valores ao inserir 3")
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
    @DisplayName("Método listar retorna lista vazia se a lista está vazia")
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

    @Test
    @DisplayName("No método findById quando id válido retorna faixaEtaria do id")
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
    @DisplayName("No método findById quando id não existente joga uma exceção")
    void findByIdNaoExistente() {

    }

    @Test
    @DisplayName("No método findById quando id inválido retorna uma exceção")
    void findByIdInvalido() {
    }

    @Test
    @DisplayName("No método create quando criado com sucesso adiciona no banco")
    void createComSucesso() {
    }

    @Test
    @DisplayName("No método create quando criado com valores inválidos retorna uma exceção")
    void createValorInvalido() {

    }

    @Test
    @DisplayName("No método update e o método atualiza corretamente uma FaixaEtaria existente  retorna 204.")
    void updateAtualizaComSucesso() {
    }

    @Test
    @DisplayName("No método update e o método não encontra o id e retorna 404.")
    void updateNaoEncontraId() {
    }

    @Test
    @DisplayName("No método update, o id é inválido e retorna 400.")
    void updateIdInvalido() {
    }

    @Test
    @DisplayName("No método delete, o id não foi encontrado e retorna 404")
    void deleteIdNaoEncontrado() {
    }

    @Test
    @DisplayName("No método delete, a exclusão é bem sucedida e retorna 204")
    void deleteExclusaoBemSucedida() {
    }

    @Test
    @DisplayName("No método delete, o id de exclusão é inválido e retornar 400")
    void deleteIdInvalido() {
    }
}