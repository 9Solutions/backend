package com.example.apirestv2.service.pedido;

import com.example.apirestv2.domain.doador.Doador;
import com.example.apirestv2.domain.pedido.Pedido;
import com.example.apirestv2.domain.pedido.repository.PedidoRepository;
import com.example.apirestv2.service.doador.DoadorService;
import com.example.apirestv2.service.pedido.dto.PedidoPatchDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {

    @InjectMocks
    private PedidoService service;

    @Mock
    private PedidoRepository repository;

    @Mock
    private DoadorService doadorService;

    @Test
    @DisplayName("Deve retornar lista de 3 pedidos")
    void deveRetornarListaDe3Pedidos() {
        List<Pedido> pedidos = List.of(
                new Pedido(),
                new Pedido(),
                new Pedido()
        );

        Mockito.when(repository.findAll()).thenReturn(pedidos);

        List<Pedido> pedidosRetorno = service.listAll();

        assertEquals(pedidos.size(), pedidosRetorno.size());
        assertEquals(3, pedidosRetorno.size());
        assertFalse(pedidosRetorno.isEmpty());
        assertEquals(pedidos.get(0).getId(), pedidosRetorno.get(0).getId());

        Mockito.verify(repository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Deve retornar lista de pedidos vazia")
    void retornaListaDePedidosVazia() {
        List<Pedido> pedidos = List.of();

        Mockito.when(repository.findAll()).thenReturn(pedidos);

        List<Pedido> pedidosRetorno = service.listAll();

        assertEquals(pedidos.size(), pedidosRetorno.size());
        assertEquals(0, pedidosRetorno.size());
        assertTrue(pedidosRetorno.isEmpty());

        Mockito.verify(repository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Deve retornar pedido por id")
    void deveRetornarPedidoPorId() {
        Pedido pedido = new Pedido();
        Integer id = 1;

        Mockito.when(repository.findById(id)).thenReturn(java.util.Optional.of(pedido));

        Pedido pedidoRetorno = service.listById(id);

        assertEquals(pedido.getId(), pedidoRetorno.getId());

        Mockito.verify(repository, Mockito.times(1)).findById(id);
    }

    @Test
    @DisplayName("Deve retornar erro ao buscar pedido por id")
    void deveRetornarErroAoBuscarPedidoPorId() {
        Integer id = 1;

        Mockito.when(repository.findById(id)).thenReturn(java.util.Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.listById(id));

        Mockito.verify(repository, Mockito.times(1)).findById(id);
    }

    @Test
    @DisplayName("Deve retornar lista de pedidos por status")
    void deveRetornarListaDePedidosPorStatus() {
        List<Pedido> pedidos = List.of(
                new Pedido(),
                new Pedido(),
                new Pedido()
        );
        Integer status = 1;

        Mockito.when(repository.buscarPorStatusPedido(status)).thenReturn(pedidos);

        List<Pedido> pedidosRetorno = service.listByStatus(status);

        assertEquals(pedidos.size(), pedidosRetorno.size());
        assertEquals(3, pedidosRetorno.size());
        assertFalse(pedidosRetorno.isEmpty());
        assertEquals(pedidos.get(0).getId(), pedidosRetorno.get(0).getId());

        Mockito.verify(repository, Mockito.times(1)).buscarPorStatusPedido(status);
    }

    @Test
    @DisplayName("Deve retornar pedido criado")
    void deveRetornarPedidoCriado() {
        Pedido pedido = new Pedido();
        Long idDoador = 1L;

        Doador doador = new Doador();
        Mockito.when(doadorService.buscarPorId(idDoador)).thenReturn(doador);

        Mockito.when(repository.save(any(Pedido.class))).thenReturn(pedido);

        Pedido pedidoRetorno = service.create(pedido, idDoador);

        assertEquals(pedido.getId(), pedidoRetorno.getId());

        Mockito.verify(repository, Mockito.times(1)).save(any(Pedido.class));
    }

    @Test
    @DisplayName("Deve retornar status do pedido alterado")
    void deveRetornarStatusChange() {
        Pedido pedido = new Pedido();
        PedidoPatchDTO change = new PedidoPatchDTO();
        Integer id = 1;

        Mockito.when(repository.findById(id)).thenReturn(java.util.Optional.of(pedido));
        Mockito.when(repository.save(pedido)).thenReturn(pedido);

        Pedido pedidoRetorno = service.statusChange(id, change);

        assertEquals(pedido, pedidoRetorno);

        Mockito.verify(repository, Mockito.times(1)).findById(id);
        Mockito.verify(repository, Mockito.times(1)).save(pedido);
    }

    @Test
    @DisplayName("Deve retornar erro ao alterar status do pedido")
    void deveRetornarErroAoAlterarStatusDoPedido() {
        PedidoPatchDTO change = new PedidoPatchDTO();
        Integer id = 1;

        Mockito.when(repository.findById(id)).thenReturn(java.util.Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.statusChange(id, change));

        Mockito.verify(repository, Mockito.times(1)).findById(id);
    }

}
