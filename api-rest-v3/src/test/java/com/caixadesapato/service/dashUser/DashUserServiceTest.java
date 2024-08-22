package com.caixadesapato.service.dashUser;

import com.caixadesapato.api.model.view.*;
import com.caixadesapato.api.repository.view.*;
import com.caixadesapato.api.service.DashUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DashUserServiceTest {

    @Mock
    private VwCaixasAtrasadasRepository vwCaixasAtrasadasRepository;

    @Mock
    private VwCaixasEmMontagemRepository vwCaixasEmMontagemRepository;

    @Mock
    private VwCaixasParaEntregarRepository caixasParaEntregarRepository;

    @Mock
    private VwQtdeDoacoesPorMesRepository vwQtdDoacoesPorMesRepository;

    @Mock
    private VwQtdePedidosPorFaixaEtariaRepository vwQtdPedidosPorFaixaEtariaRepository;

    @InjectMocks
    private DashUserService dashUserService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCaixasAtrasadas() {
        // Dados de teste
        List<VwCaixasAtrasadas> caixasAtrasadas = List.of(new VwCaixasAtrasadas(), new VwCaixasAtrasadas());

        // Configuração dos mocks
        when(vwCaixasAtrasadasRepository.findAll()).thenReturn(caixasAtrasadas);

        // Chamada do método
        List<VwCaixasAtrasadas> result = dashUserService.getCaixasAtrasadas();

        // Verificações
        assertNotNull(result);
        assertEquals(caixasAtrasadas, result);
        verify(vwCaixasAtrasadasRepository).findAll();
    }

    @Test
    void testGetCaixasEmMontagem() {
        // Dados de teste
        List<VwCaixasEmMontagem> caixasEmMontagem = List.of(new VwCaixasEmMontagem(), new VwCaixasEmMontagem());

        // Configuração dos mocks
        when(vwCaixasEmMontagemRepository.findAll()).thenReturn(caixasEmMontagem);

        // Chamada do método
        List<VwCaixasEmMontagem> result = dashUserService.getCaixasEmMontagem();

        // Verificações
        assertNotNull(result);
        assertEquals(caixasEmMontagem, result);
        verify(vwCaixasEmMontagemRepository).findAll();
    }

    @Test
    void testGetCaixasParaEntregar() {
        // Dados de teste
        List<VwCaixasParaEntregar> caixasParaEntregar = List.of(new VwCaixasParaEntregar(), new VwCaixasParaEntregar());

        // Configuração dos mocks
        when(caixasParaEntregarRepository.findAll()).thenReturn(caixasParaEntregar);

        // Chamada do método
        List<VwCaixasParaEntregar> result = dashUserService.getCaixasParaEntregar();

        // Verificações
        assertNotNull(result);
        assertEquals(caixasParaEntregar, result);
        verify(caixasParaEntregarRepository).findAll();
    }

    @Test
    void testFindByAnoAndMes() {
        // Dados de teste
        Integer ano = 2024;
        Integer mes = 8;
        List<VwQtdeDoacoesPorMes> doacoesPorMes = List.of(new VwQtdeDoacoesPorMes(), new VwQtdeDoacoesPorMes());

        // Configuração dos mocks
        when(vwQtdDoacoesPorMesRepository.findByAnoAndMes(ano, mes)).thenReturn(doacoesPorMes);

        // Chamada do método
        List<VwQtdeDoacoesPorMes> result = dashUserService.findByAnoAndMes(ano, mes);

        // Verificações
        assertNotNull(result);
        assertEquals(doacoesPorMes, result);
        verify(vwQtdDoacoesPorMesRepository).findByAnoAndMes(ano, mes);
    }

    @Test
    void testFindByAno() {
        // Dados de teste
        Integer ano = 2024;
        List<VwQtdePedidosPorFaixaEtaria> pedidosPorFaixaEtaria = List.of(new VwQtdePedidosPorFaixaEtaria(), new VwQtdePedidosPorFaixaEtaria());

        // Configuração dos mocks
        when(vwQtdPedidosPorFaixaEtariaRepository.findByAno(ano)).thenReturn(pedidosPorFaixaEtaria);

        // Chamada do método
        List<VwQtdePedidosPorFaixaEtaria> result = dashUserService.findByAnoAndMes(ano);

        // Verificações
        assertNotNull(result);
        assertEquals(pedidosPorFaixaEtaria, result);
        verify(vwQtdPedidosPorFaixaEtariaRepository).findByAno(ano);
    }
}
