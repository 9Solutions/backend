package com.caixadesapato.service.statusPedido;

import com.caixadesapato.api.model.StatusPedido;
import com.caixadesapato.api.repository.StatusPedidoRepository;
import com.caixadesapato.api.service.StatusPedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class StatusPedidoServiceTest {

    @Mock
    private StatusPedidoRepository statusPedidoRepository;

    @InjectMocks
    private StatusPedidoService statusPedidoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_DeveRetornarStatusPedidoQuandoEncontrado() {
        // Arrange
        Integer id = 1;
        StatusPedido statusPedido = new StatusPedido();
        statusPedido.setId(id);

        when(statusPedidoRepository.findById(id)).thenReturn(Optional.of(statusPedido));

        // Act
        StatusPedido result = statusPedidoService.findById(id);

        // Assert
        assertEquals(statusPedido, result);
    }

    @Test
    void findById_DeveLancarResponseStatusExceptionQuandoNaoEncontrado() {
        // Arrange
        Integer id = 1;

        when(statusPedidoRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> {
            statusPedidoService.findById(id);
        });
    }
}
