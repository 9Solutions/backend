package com.example.apirestv2.service;

import com.example.apirestv2.domain.caixa.Caixa;
import com.example.apirestv2.domain.caixa.repository.CaixaRepository;
import com.example.apirestv2.domain.itemCaixa.ItemCaixa;
import com.example.apirestv2.domain.pedido.Pedido;
import com.example.apirestv2.service.caixa.CaixaService;
import com.example.apirestv2.service.itemCaixa.ItemCaixaService;
import com.example.apirestv2.service.pedido.PedidoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class CaixaServiceTest {

    @InjectMocks
    private CaixaService service;

    @Mock
    private CaixaRepository repository;

    @Mock
    private ItemCaixaService itemCaixaService;

    @Mock
    private PedidoService pedidoService;

    @Test
    @DisplayName("Deve retornar a caixa criada")
    void deveRetornarCaixaCriada() {
        Caixa caixa = new Caixa();
        Integer idPedido = 1;
        int[] listIdsProdutos = {1, 2, 3};

        Pedido pedido = new Pedido();
        Mockito.when(pedidoService.listById(idPedido)).thenReturn(pedido);

        Mockito.when(repository.save(any(Caixa.class))).thenReturn(caixa);

        List<ItemCaixa> items = Arrays.asList(new ItemCaixa(), new ItemCaixa(), new ItemCaixa());
        Mockito.when(itemCaixaService.insertItems(any(Caixa.class), any(int[].class))).thenReturn(items);

        Caixa caixaRetorno = service.create(caixa, listIdsProdutos, idPedido);

        assertEquals(caixa.getId(), caixaRetorno.getId());

        Mockito.verify(repository, Mockito.times(1)).save(any(Caixa.class));
    }

    @Test
    @DisplayName("Deve retornar lista com 3 caixas")
    void deveRetornarListaCaixa() {
        // GIVEN
        List<Caixa> caixas = List.of(
            new Caixa(),
            new Caixa(),
            new Caixa()
        );

        Mockito.when(service.listAll()).thenReturn(caixas);

        List<Caixa> caixasRetorno = service.listAll();

        assertEquals(caixas.size(), caixasRetorno.size());
        assertEquals(3, caixasRetorno.size());
        assertFalse(caixasRetorno.isEmpty());
        assertEquals(caixas.get(0).getId(), caixasRetorno.get(0).getId());

        Mockito.verify(repository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Retorna lista vazia")
    void deveRetornarListaVazia() {
        List<Caixa> caixas = List.of();

        Mockito.when(service.listAll()).thenReturn(caixas);

        List<Caixa> caixasRetorno = service.listAll();

        assertEquals(0, caixasRetorno.size());
        assertEquals(caixas.size(), caixasRetorno.size());
        assertEquals(caixas.isEmpty(), caixasRetorno.isEmpty());

        Mockito.verify(repository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Deve retornar caixa por id")
    void deveRetornarCaixaPorId() {
        Caixa caixa = new Caixa();
        Integer id = 1;

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(caixa));

        Caixa caixaRetorno = service.listByID(id);

        assertEquals(caixa.getId(), caixaRetorno.getId());

        Mockito.verify(repository, Mockito.times(1)).findById(id);
    }

    @Test
    @DisplayName("Deve retornar caixa atualizada")
    void deveRetornarCaixaAtualizada() {
        Caixa caixa = new Caixa();
        Caixa caixaNova = new Caixa();
        Integer idPedido = 1;
        int[] listIdsProdutos = {1, 2, 3};

        Pedido pedido = new Pedido();
        Mockito.when(pedidoService.listById(idPedido)).thenReturn(pedido);

        Mockito.when(repository.save(any(Caixa.class))).thenReturn(caixa);

        List<ItemCaixa> items = Arrays.asList(new ItemCaixa(), new ItemCaixa(), new ItemCaixa());
        Mockito.when(itemCaixaService.insertItems(any(Caixa.class), any(int[].class))).thenReturn(items);

        Caixa caixaRetorno = service.create(caixaNova, listIdsProdutos, idPedido);

        assertEquals(caixa.getId(), caixaRetorno.getId());
        Mockito.verify(repository, Mockito.times(1)).save(any(Caixa.class));
    }
}
