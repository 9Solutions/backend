package com.caixadesapato.service.etapaCaixa;

import com.caixadesapato.api.model.Caixa;
import com.caixadesapato.api.model.EtapaCaixa;
import com.caixadesapato.api.model.StatusCaixa;
import com.caixadesapato.api.repository.EtapaCaixaRepository;
import com.caixadesapato.api.repository.StatusCaixaRepository;
import com.caixadesapato.api.service.EtapaCaixaService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EtapaCaixaServiceTest {

    @Mock
    private EtapaCaixaRepository etapaRepository;

    @Mock
    private StatusCaixaRepository statusCaixaRepository;

    @InjectMocks
    private EtapaCaixaService etapaCaixaService;

    public EtapaCaixaServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void setEtapaCaixa_DeveSalvarEtapaCaixaComStatusCorreto() {
        // Arrange
        Caixa caixa = new Caixa();
        Integer status = 1;
        StatusCaixa statusCaixa = new StatusCaixa();
        statusCaixa.setId(status);

        when(statusCaixaRepository.findById(status)).thenReturn(Optional.of(statusCaixa));

        // Act
        etapaCaixaService.setEtapaCaixa(caixa, status);

        // Assert
        ArgumentCaptor<EtapaCaixa> etapaCaixaCaptor = ArgumentCaptor.forClass(EtapaCaixa.class);
        verify(etapaRepository).save(etapaCaixaCaptor.capture());
        EtapaCaixa capturedEtapaCaixa = etapaCaixaCaptor.getValue();

        assertEquals(caixa, capturedEtapaCaixa.getCaixa());
        assertEquals(statusCaixa, capturedEtapaCaixa.getStatus());
    }

    @Test
    void etapas_DeveRetornarListaDeEtapasCaixa() {
        // Arrange
        int idCaixa = 1;
        List<EtapaCaixa> expectedEtapas = List.of(new EtapaCaixa(), new EtapaCaixa());

        when(etapaRepository.findByCaixa_Id(idCaixa)).thenReturn(expectedEtapas);

        // Act
        List<EtapaCaixa> actualEtapas = etapaCaixaService.etapas(idCaixa);

        // Assert
        assertEquals(expectedEtapas.size(), actualEtapas.size());
        assertEquals(expectedEtapas, actualEtapas);
    }

    @Test
    void setEtapaCaixa_StatusNaoEncontrado_DeveLancarExcecao() {
        // Arrange
        Caixa caixa = new Caixa();
        Integer status = 1;

        when(statusCaixaRepository.findById(status)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> etapaCaixaService.setEtapaCaixa(caixa, status));
        verify(etapaRepository, never()).save(any(EtapaCaixa.class));
    }
}
