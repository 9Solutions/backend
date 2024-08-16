/*
package com.caixadesapato.service.doador;


import com.caixadesapato.api.dto.doador.*;
import com.caixadesapato.api.model.Doador;
import com.caixadesapato.api.repository.DoadorRepository;
import com.caixadesapato.api.service.DoadorService;
import com.caixadesapato.api.config.auth.GerenciadorTokenJwt;
import com.caixadesapato.api.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DoadorServiceTest {

    @Mock
    private DoadorRepository doadorRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private DoadorService doadorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCadastrar() {
        // Dados de teste
        DoadorCriacaoDTO doadorCriacaoDto = new DoadorCriacaoDTO();
        doadorCriacaoDto.setEmail("test@example.com");
        doadorCriacaoDto.setSenha("password");

        Doador doador = new Doador();
        doador.setEmail(doadorCriacaoDto.getEmail());
        doador.setSenha(doadorCriacaoDto.getSenha());

        // Configuração dos mocks
        when(passwordEncoder.encode(doador.getSenha())).thenReturn("encodedPassword");

        // Chamada do método
        doadorService.cadastrar(doadorCriacaoDto);

        // Verificações
        verify(doadorRepository).save(doador);
    }

    @Test
    void testLoginSuccess() {
        // Dados de teste
        DoadorLoginDTO doadorLoginDto = new DoadorLoginDTO("test@example.com", "password");
        //doadorLoginDto.setEmail("test@example.com");
        //doadorLoginDto.setSenha("password");

        Doador doador = new Doador();
        doador.setEmail(doadorLoginDto.getEmail());
        doador.setSenha("encodedPassword");

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(doadorRepository.findByEmail(doadorLoginDto.getEmail())).thenReturn(Optional.of(doador));
        when(gerenciadorTokenJwt.generateToken(authentication)).thenReturn("token");
        when(DoadorMapper.toTokenDto(doador, "token")).thenReturn(new DoadorTokenDTO());

        // Chamada do método
        DoadorTokenDTO result = doadorService.login(doadorLoginDto);

        // Verificações
        assertNotNull(result);
        assertEquals("token", result.getToken());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(doadorRepository).findByEmail(doadorLoginDto.getEmail());
    }

    @Test
    void testLoginUserNotFound() {
        // Dados de teste
        //DoadorLoginDTO doadorLoginDto = new DoadorLoginDTO();
        //doadorLoginDto.setEmail("test@example.com");
        //doadorLoginDto.setSenha("password");

        // Configuração dos mocks
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(mock(Authentication.class));
        when(doadorRepository.findByEmail(doadorLoginDto.getEmail())).thenReturn(Optional.empty());

        // Chamada do método e verificação
        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> doadorService.login(doadorLoginDto));
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
    }

    @Test
    void testAlterarSenhaSuccess() throws Exception {
        // Dados de teste
        String email = "test@example.com";
        DoadorAlteracaoSenhaDTO alterarSenhaDTO = new DoadorAlteracaoSenhaDTO();
        alterarSenhaDTO.setSenhaAtual("currentPassword");
        alterarSenhaDTO.setNovaSenha("newPassword");
        alterarSenhaDTO.setRepetirNovaSenha("newPassword");

        Doador doador = new Doador();
        doador.setEmail(email);
        doador.setSenha("encodedCurrentPassword");

        // Configuração dos mocks
        when(doadorRepository.findByEmail(email)).thenReturn(Optional.of(doador));
        when(passwordEncoder.matches(alterarSenhaDTO.getSenhaAtual(), doador.getSenha())).thenReturn(true);
        when(passwordEncoder.encode(alterarSenhaDTO.getNovaSenha())).thenReturn("encodedNewPassword");

        // Chamada do método
        doadorService.alterarSenha(email, alterarSenhaDTO);

        // Verificações
        verify(doadorRepository).save(doador);
    }

    @Test
    void testAlterarSenhaCurrentPasswordIncorrect() {
        // Dados de teste
        String email = "test@example.com";
        DoadorAlteracaoSenhaDTO alterarSenhaDTO = new DoadorAlteracaoSenhaDTO();
        alterarSenhaDTO.setSenhaAtual("currentPassword");
        alterarSenhaDTO.setNovaSenha("newPassword");
        alterarSenhaDTO.setRepetirNovaSenha("newPassword");

        Doador doador = new Doador();
        doador.setEmail(email);
        doador.setSenha("encodedOldPassword");

        // Configuração dos mocks
        when(doadorRepository.findByEmail(email)).thenReturn(Optional.of(doador));
        when(passwordEncoder.matches(alterarSenhaDTO.getSenhaAtual(), doador.getSenha())).thenReturn(false);

        // Chamada do método e verificação
        Exception thrown = assertThrows(Exception.class, () -> doadorService.alterarSenha(email, alterarSenhaDTO));
        assertEquals("Senha atual incorreta", thrown.getMessage());
    }

    @Test
    void testAlterarSenhaNewPasswordsDoNotMatch() {
        // Dados de teste
        String email = "test@example.com";
        DoadorAlteracaoSenhaDTO alterarSenhaDTO = new DoadorAlteracaoSenhaDTO();
        alterarSenhaDTO.setSenhaAtual("currentPassword");
        alterarSenhaDTO.setNovaSenha("newPassword");
        alterarSenhaDTO.setRepetirNovaSenha("differentNewPassword");

        Doador doador = new Doador();
        doador.setEmail(email);
        doador.setSenha("encodedCurrentPassword");

        // Configuração dos mocks
        when(doadorRepository.findByEmail(email)).thenReturn(Optional.of(doador));
        when(passwordEncoder.matches(alterarSenhaDTO.getSenhaAtual(), doador.getSenha())).thenReturn(true);

        // Chamada do método e verificação
        Exception thrown = assertThrows(Exception.class, () -> doadorService.alterarSenha(email, alterarSenhaDTO));
        assertEquals("A nova senha e a repetição da nova senha não são iguais", thrown.getMessage());
    }

    @Test
    void testBuscarPorIdSuccess() {
        // Dados de teste
        Long id = 1L;
        Doador doador = new Doador();
        doador.setId(id);

        // Configuração dos mocks
        when(doadorRepository.findById(id)).thenReturn(Optional.of(doador));

        // Chamada do método
        Doador result = doadorService.buscarPorId(id);

        // Verificações
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(doadorRepository).findById(id);
    }

    @Test
    void testBuscarPorIdNotFound() {
        // Dados de teste
        Long id = 1L;

        // Configuração dos mocks
        when(doadorRepository.findById(id)).thenReturn(Optional.empty());

        // Chamada do método e verificação
        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> doadorService.buscarPorId(id));
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
    }

    @Test
    void testUpdateListener() {
        // Dados de teste
        String email = "test@example.com";
        String eventType = "eventType";

        // Chamada do método
        doadorService.updateListener(email, eventType);

        // Verificação
        verify(emailService).sendMail(eq(email), eq("Status alterado"), anyString());
    }
}
*/