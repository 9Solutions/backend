package com.caixadesapato.service.doador;

import com.caixadesapato.api.config.auth.GerenciadorTokenJwt;
import com.caixadesapato.api.dto.doador.*;
import com.caixadesapato.api.model.Doador;
import com.caixadesapato.api.repository.DoadorRepository;
import com.caixadesapato.api.service.DoadorService;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    void testLogin() {
        DoadorLoginDTO doadorLoginDTO = new DoadorLoginDTO("email@example.com", "senha");
        Doador doador = new Doador();
        doador.setEmail(doadorLoginDTO.getEmail());
        doador.setSenha("senhaCriptografada");

        Authentication authentication = mock(Authentication.class);

        when(doadorRepository.findByEmail(doadorLoginDTO.getEmail())).thenReturn(Optional.of(doador));
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(gerenciadorTokenJwt.generateToken(authentication)).thenReturn("fakeToken");

        DoadorTokenDTO tokenDTO = doadorService.login(doadorLoginDTO);

        assertNotNull(tokenDTO);
        assertEquals("fakeToken", tokenDTO.getToken());
        verify(doadorRepository, times(1)).findByEmail(doadorLoginDTO.getEmail());
    }

    @Test
    void testLoginEmailNaoCadastrado() {
        DoadorLoginDTO doadorLoginDTO = new DoadorLoginDTO("email@example.com", "senha");

        when(doadorRepository.findByEmail(doadorLoginDTO.getEmail())).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            doadorService.login(doadorLoginDTO);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Email do doador não cadastrado", exception.getReason());
    }

    @Test
    void testBuscarPorId() {
        Long id = 1L;
        Doador doador = new Doador();
        doador.setId(id);

        when(doadorRepository.findById(id)).thenReturn(Optional.of(doador));

        Doador result = doadorService.buscarPorId(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void testBuscarPorIdNaoEncontrado() {
        Long id = 1L;

        when(doadorRepository.findById(id)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            doadorService.buscarPorId(id);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
}
