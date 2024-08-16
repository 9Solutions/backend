package com.caixadesapato.service.caixa;

import com.caixadesapato.api.dto.caixa.CaixaUpdateDTO;
import com.caixadesapato.api.model.*;
import com.caixadesapato.api.repository.CaixaRepository;
import com.caixadesapato.api.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class CaixaServiceTest {

    @Mock
    private CaixaRepository caixaRepository;

    @Mock
    private ItemCaixaService itemCaixaService;

    @Mock
    private PedidoService pedidoService;

    @Mock
    private EtapaCaixaService etapaService;

    @Mock
    private DoadorService doadorService;

    @Mock
    private FaixaEtariaService faixaEtariaService;

    @InjectMocks
    private CaixaService caixaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testSaveSuccess() {
        // Definindo o ID da Caixa
        Caixa novaCaixa = new Caixa();
        novaCaixa.setId(1);  // Defina um ID fixo para a caixa
        Pedido pedido = new Pedido();
        FaixaEtaria faixaEtaria = new FaixaEtaria();
        List<ItemCaixa> itens = List.of(new ItemCaixa());
        List<EtapaCaixa> etapas = List.of(new EtapaCaixa());

        when(pedidoService.listById(anyInt())).thenReturn(pedido);
        when(faixaEtariaService.findById(anyInt())).thenReturn(faixaEtaria);
        when(caixaRepository.save(any(Caixa.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(itemCaixaService.insertItems(any(Caixa.class), any(int[].class))).thenReturn(itens);
        when(etapaService.etapas(anyInt())).thenReturn(etapas);

        Caixa result = caixaService.save(novaCaixa, new int[]{1, 2}, 1, 1);

        assertNotNull(result);
        assertEquals(1, result.getId());  // Verifica se o ID foi corretamente atribuído
        assertEquals(itens, result.getItens());
        assertEquals(etapas, result.getEtapas());

        // Verifica se a data de criação é a data atual com uma pequena margem de erro
        LocalDate now = LocalDate.now();
        assertTrue(result.getDataCriacao().isEqual(now) || result.getDataCriacao().isAfter(now.minusDays(1)));

        verify(caixaRepository).save(novaCaixa);
    }



    @Test
    void testSaveNullCaixa() {
        Caixa result = caixaService.save(null, new int[]{}, null, null);

        assertNull(result);
        verifyNoInteractions(pedidoService, faixaEtariaService, caixaRepository, itemCaixaService, etapaService);
    }

    @Test
    void testListAll() {
        Caixa caixa1 = new Caixa();
        Caixa caixa2 = new Caixa();
        List<Caixa> caixas = List.of(caixa1, caixa2);

        when(caixaRepository.findAll()).thenReturn(caixas);

        List<Caixa> result = caixaService.listAll();

        assertEquals(caixas, result);
        verify(caixaRepository).findAll();
    }


    @Test
    void testListByIDSuccess() {
        Caixa caixa = new Caixa();

        when(caixaRepository.findById(anyInt())).thenReturn(Optional.of(caixa));

        Caixa result = caixaService.listByID(1);

        assertEquals(caixa, result);
        verify(caixaRepository).findById(1);
    }

    @Test
    void testListByIDNotFound() {
        when(caixaRepository.findById(anyInt())).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> caixaService.listByID(1));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Não encontrado", exception.getReason());
    }

    @Test
    void testUpdateSuccess() {
        Caixa caixa = new Caixa();
        CaixaUpdateDTO caixaUpdateDTO = new CaixaUpdateDTO();
        caixaUpdateDTO.setCarta("New Carta");
        caixaUpdateDTO.setUrl("New URL");
        caixaUpdateDTO.setQuantidade(10);

        when(caixaRepository.findById(anyInt())).thenReturn(Optional.of(caixa));
        when(caixaRepository.save(any(Caixa.class))).thenReturn(caixa);

        Caixa result = caixaService.update(1, caixaUpdateDTO);

        assertEquals("New Carta", result.getCarta());
        assertEquals("New URL", result.getUrl());
        assertEquals(10, result.getQuantidade());
        verify(caixaRepository).save(caixa);
    }

    @Test
    void testUpdateNotFound() {
        CaixaUpdateDTO caixaUpdateDTO = new CaixaUpdateDTO();

        when(caixaRepository.findById(anyInt())).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> caixaService.update(1, caixaUpdateDTO));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Não encontrado", exception.getReason());
    }

    @Test
    void testStatusChangeSuccess() {
        Caixa caixa = new Caixa();
        Pedido pedido = new Pedido();
        Doador doador = new Doador();
        pedido.setDoador(doador);
        caixa.setPedido(pedido);

        when(caixaRepository.findById(anyInt())).thenReturn(Optional.of(caixa));
        doNothing().when(etapaService).setEtapaCaixa(any(Caixa.class), anyInt());
        doNothing().when(doadorService).updateListener(anyString(), anyString());

        caixaService.statusChange(1, 2);

        verify(etapaService).setEtapaCaixa(caixa, 2);
        verify(doadorService).updateListener(doador.getEmail(), "Caixa");
    }


    @Test
    void testStatusChangeNotFound() {
        when(caixaRepository.findById(anyInt())).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> caixaService.statusChange(1, 2));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Não encontrado", exception.getReason());
    }
}
