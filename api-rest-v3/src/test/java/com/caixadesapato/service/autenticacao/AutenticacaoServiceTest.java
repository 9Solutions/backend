package com.caixadesapato.service.autenticacao;

import com.caixadesapato.api.dto.doador.DoadorDetalhesDTO;
import com.caixadesapato.api.model.Doador;
import com.caixadesapato.api.repository.DoadorRepository;
import com.caixadesapato.api.service.AutenticacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AutenticacaoServiceTest {

    @Mock
    private DoadorRepository doadorRepository;

    @InjectMocks
    private AutenticacaoService autenticacaoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsernameSuccess() {
        // Dados de teste
        String email = "doador@example.com";
        Doador doador = new Doador();
        doador.setEmail(email);
        DoadorDetalhesDTO doadorDetalhesDTO = new DoadorDetalhesDTO(doador);

        // Configuração dos mocks
        when(doadorRepository.findByEmail(email)).thenReturn(Optional.of(doador));

        // Chamada do método
        UserDetails userDetails = autenticacaoService.loadUserByUsername(email);

        // Verificações
        assertNotNull(userDetails);
        assertTrue(userDetails instanceof DoadorDetalhesDTO);
        assertEquals(email, ((DoadorDetalhesDTO) userDetails).getUsername()); // Supondo que getUsername() está implementado
        verify(doadorRepository).findByEmail(email);
    }

    @Test
    void testLoadUserByUsernameNotFound() {
        // Dados de teste
        String email = "doador@example.com";

        // Configuração dos mocks
        when(doadorRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Chamada do método e verificação da exceção
        UsernameNotFoundException thrown = assertThrows(
                UsernameNotFoundException.class,
                () -> autenticacaoService.loadUserByUsername(email)
        );

        assertEquals(String.format("Usuário %s não encontrado", email), thrown.getMessage());
        verify(doadorRepository).findByEmail(email);
    }
}

