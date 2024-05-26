package com.example.apirestv2.service.categoria;

import com.example.apirestv2.domain.categoria.Categoria;
import com.example.apirestv2.domain.categoria.repository.CategoriaRepository;
import com.example.apirestv2.service.categoria.dto.CategoriaUpdateDTO;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)

class CategoriaServiceTest {

    @InjectMocks
    private CategoriaService service;
    @Mock
    private CategoriaRepository repository;

    // FindById
    @Test
    @DisplayName("findById: quando id válido retorna faixaEtaria do id")
    void findByIdVálido() {
        // Given
        Optional<Categoria>categoria = Optional.of(new Categoria());
        Integer id = 1;
        // when
        Mockito.when(repository.findById(id)).thenReturn(categoria);
        // then
        Categoria categoriaEncontrada = service.findById(id);
        // assert
        assertEquals(categoria.get().getNome(),categoriaEncontrada.getNome());
        Mockito.verify(repository,Mockito.times(1)).findById(id);
    }

    @Test
    @DisplayName("findById: quando id não existente joga uma exceção")
    void findByIdNaoExistente() {
        // Given
        Integer id = 500;
        Optional<Categoria>categoria = Optional.empty();

        // When
        Mockito.when(repository.findById(id)).thenReturn(categoria);

        // Then
        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, () -> service.findById(id));
        assertEquals("404 NOT_FOUND \"Não encontrado\"", responseStatusException.getMessage());
    }


    @Test
    @DisplayName("findById: quando id inválido retorna uma exceção")
    void findByIdInvalido() {
        // Given
        Optional<Categoria>categoria = Optional.empty();
        Integer id = null;
        // when
        Mockito.when(repository.findById(id)).thenReturn(categoria);
        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, () -> service.findById(id));
        assertEquals("404 NOT_FOUND \"Não encontrado\"",responseStatusException.getMessage());
    }

    // MÈTODO LISTAR
    @Test
    @DisplayName("Listar: retorna uma lista com 3 valores ao inserir 3")
    void listar3produtos() {
        List<Categoria> categorias = List.of(new Categoria(),new Categoria(),new Categoria());
        // when
        Mockito.when(repository.findAll()).thenReturn(categorias);
        // then
        List<Categoria> categoriaList = service.listAll();
        // se tem 3 retorna 3
        // assert
        assertFalse(categoriaList.isEmpty());
        assertEquals(categoriaList.size(),3);
        assertEquals(categorias.get(0).getNome(),categorias.get(0).getNome());
        assertEquals(categorias.get(0).getId(),categorias.get(0).getId());
        assertEquals(categorias.get(0).getProdutos(),categorias.get(0).getProdutos());
    }


    @Test
    @DisplayName("Listar: retorna lista vazia se a lista está vazia")
    void listarVazio() {
        // Given
        List<Categoria> lista = Collections.emptyList();
        // when
        Mockito.when(repository.findAll()).thenReturn(lista);
        // then
        List<Categoria> categorias = service.listAll();
        // assert
        assertTrue(categorias.isEmpty());
    }

    // CREATE
    @Test
    @DisplayName("Create: deve retornar o objeto salvo")
    void testCreateSalvaFaixa() {
        // GIVEN
        Categoria categoria = new Categoria();
        categoria.setNome("Categoria 1");
        categoria.setProdutos(null);

        Categoria categoriaSalva = new Categoria();
        categoriaSalva.setId(1);
        categoria.setNome("Categoria 1");
        categoriaSalva.setProdutos(null);

        // WHEN
        Mockito.when(repository.save(categoria)).thenReturn(categoriaSalva);

        Categoria result = service.create(categoria);

        // THEN
        assertEquals(categoriaSalva.getId(),result.getId());
        assertEquals(categoriaSalva.getNome(), result.getNome());
        assertEquals(categoriaSalva.getProdutos(), result.getProdutos());
        Mockito.verify(repository, Mockito.times(1)).save(categoria);
    }

    // MÉTODO UPDATE

    @Test
    @DisplayName("Update: atualizado com sucesso")
    public void StestUpdateSuccess() {
        Integer id = 1;
        CategoriaUpdateDTO categoriaUpdateDTO = new CategoriaUpdateDTO();
        categoriaUpdateDTO.setNome("Nova Categoria");

        Categoria categoria = new Categoria();
        categoria.setId(id);
        categoria.setNome("Antiga Categoria");

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(categoria));
        Mockito.when(repository.save(Mockito.any(Categoria.class))).thenReturn(categoria);

        Categoria categoriaAtualizada = service.update(id,categoriaUpdateDTO);

        assertEquals("Nova Categoria", categoriaAtualizada.getNome());
        assertEquals("Nova Categoria", categoriaAtualizada.getNome());
        Mockito.verify(repository, Mockito.times(1)).findById(id);
        Mockito.verify(repository, Mockito.times(1)).save(categoria);
    }

    @Test
    @DisplayName("Update: método não encontra o id e retorna uma exceção.")
    void updateNaoEncontraId() {
        Integer id = 1;
        CategoriaUpdateDTO categoriaUpdateDTO = new CategoriaUpdateDTO();
        categoriaUpdateDTO.setNome("Nova faixa");

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            service.update(id, categoriaUpdateDTO);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        Mockito.verify(repository, Mockito.times(1)).findById(id);
        Mockito.verify(repository, Mockito.times(0)).save(Mockito.any(Categoria.class));

    }

    @Test
    @DisplayName("Update: o id é inválido e retorna uma exceção.")
    void updateIdInvalido() {
        // Given
        Optional<Categoria> categoria = Optional.empty();
        Integer id = null;
        // when
        Mockito.when(repository.findById(id)).thenReturn(categoria);

        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, () -> service.findById(id));
        assertEquals("404 NOT_FOUND \"Não encontrado\"",responseStatusException.getMessage());
    }


    // MÈTODO DELETE

    @Test
    @DisplayName("Delete: dado que id é válido, é deletado com sucesso")
    public void testDeleteSuccess() {
        Integer id = 1;
        Categoria categoria = new Categoria();
        categoria.setId(id);
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(categoria));
        service.delete(id);
        Mockito.verify(repository, Mockito.times(1)).findById(id);
        Mockito.verify(repository, Mockito.times(1)).delete(categoria);
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
                ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, () -> service.delete(id));
                assertEquals(HttpStatus.NOT_FOUND,responseStatusException.getStatusCode());
                Mockito.verify(repository, Mockito.times(0)).delete(Mockito.any(Categoria.class));
            }
        }