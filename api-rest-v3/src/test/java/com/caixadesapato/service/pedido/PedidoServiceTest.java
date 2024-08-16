package com.caixadesapato.service.pedido;

import com.caixadesapato.api.dto.pedido.PedidoPatchDTO;
import com.caixadesapato.api.model.Doador;
import com.caixadesapato.api.model.Pedido;
import com.caixadesapato.api.model.StatusPedido;
import com.caixadesapato.api.model.view.VwFiltroPedido;
import com.caixadesapato.api.repository.PedidoRepository;
import com.caixadesapato.api.repository.view.VwFiltrosPedidosRepository;
import com.caixadesapato.api.service.DoadorService;
import com.caixadesapato.api.service.PedidoService;
import com.caixadesapato.api.service.StatusPedidoService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private DoadorService doadorService;

    @Mock
    private StatusPedidoService statusPedidoService;

    @Mock
    private VwFiltrosPedidosRepository vwFiltrosPedidosRepository;

    @InjectMocks
    private PedidoService pedidoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListAll() {
        List<Pedido> pedidos = List.of(new Pedido(), new Pedido());

        when(pedidoRepository.findAll()).thenReturn(pedidos);

        List<Pedido> result = pedidoService.listAll();

        assertEquals(pedidos, result);
        verify(pedidoRepository).findAll();
    }

    @Test
    void testListByIdSuccess() {
        Pedido pedido = new Pedido();
        pedido.setId(1);

        when(pedidoRepository.findById(1)).thenReturn(Optional.of(pedido));

        Pedido result = pedidoService.listById(1);

        assertNotNull(result);
        assertEquals(pedido, result);
        verify(pedidoRepository).findById(1);
    }

    @Test
    void testListByIdNotFound() {
        when(pedidoRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> pedidoService.listById(1));

        verify(pedidoRepository).findById(1);
    }

    @Test
    void testListAllDetailsByStatus() {
        Integer statusPedido = 1;
        List<Pedido> pedidos = List.of(new Pedido());

        when(pedidoRepository.findByStatusPedido_IdEquals(statusPedido)).thenReturn(pedidos);

        List<Pedido> result = pedidoService.listAllDetailsByStatus(statusPedido);

        assertEquals(pedidos, result);
        verify(pedidoRepository).findByStatusPedido_IdEquals(statusPedido);
    }

    @Test
    void testListByStatus() {
        String status = "status";
        String data = "data";
        String idPedido = "idPedido";
        List<VwFiltroPedido> filtros = List.of(new VwFiltroPedido());

        // Ajusta a configuração para corresponder à lógica do método listByStatus
        when(vwFiltrosPedidosRepository.buscaFiltros(idPedido, data + "%", status)).thenReturn(filtros);

        List<VwFiltroPedido> result = pedidoService.listByStatus(status, data, idPedido);

        assertEquals(filtros, result);
        verify(vwFiltrosPedidosRepository).buscaFiltros(idPedido, data + "%", status);
    }


    @Test
    void testCreateSuccess() {
        Pedido novoPedido = new Pedido();
        Long idDoador = 1L;
        Doador doador = new Doador();
        StatusPedido statusPedido = new StatusPedido();
        novoPedido.setStatusPedido(statusPedido); // Ajuste o pedido para definir o statusPedido

        when(doadorService.buscarPorId(idDoador)).thenReturn(doador);
        when(statusPedidoService.findById(statusPedido.getId())).thenReturn(statusPedido);
        when(pedidoRepository.save(novoPedido)).thenReturn(novoPedido);

        Pedido result = pedidoService.create(novoPedido, idDoador);

        assertNotNull(result);
        assertEquals(novoPedido, result);
        verify(doadorService).buscarPorId(idDoador);
        verify(statusPedidoService).findById(statusPedido.getId());
        verify(pedidoRepository).save(novoPedido);
    }

    @Test
    void testStatusChangeSuccess() {
        Integer id = 1;
        Integer condicao = 2;
        Pedido pedido = new Pedido();
        StatusPedido statusPedido = new StatusPedido();
        Doador doador = new Doador();
        pedido.setDoador(doador);

        when(pedidoRepository.findById(id)).thenReturn(Optional.of(pedido));
        when(statusPedidoService.findById(condicao)).thenReturn(statusPedido);
        when(pedidoRepository.save(pedido)).thenReturn(pedido);

        Pedido result = pedidoService.statusChange(id, condicao);

        assertNotNull(result);
        assertEquals(statusPedido, result.getStatusPedido());
        verify(pedidoRepository).findById(id);
        verify(statusPedidoService).findById(condicao);
        verify(pedidoRepository).save(pedido);
        verify(doadorService).updateListener(doador.getEmail(), "Pedido");
    }

    @Test
    void testStatusChangeNotFound() {
        Integer id = 1;
        Integer condicao = 2;

        when(pedidoRepository.findById(id)).thenReturn(Optional.empty());

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> pedidoService.statusChange(id, condicao)
        );

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals("Não Encontrado", thrown.getReason());
        verify(pedidoRepository).findById(id);
        verify(pedidoRepository, never()).save(any(Pedido.class));
        verify(doadorService, never()).updateListener(anyString(), anyString());
    }

    @Test
    void testCreatePedidoWithInvalidDoador() {
        Pedido novoPedido = new Pedido();
        Long idDoador = 1L;

        when(doadorService.buscarPorId(idDoador)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Doador não encontrado"));

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> pedidoService.create(novoPedido, idDoador)
        );

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals("Doador não encontrado", thrown.getReason());
        verify(doadorService).buscarPorId(idDoador);
        verify(statusPedidoService, never()).findById(anyInt());
        verify(pedidoRepository, never()).save(any(Pedido.class));
    }
}