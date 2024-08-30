package com.caixadesapato.controller.qrcodeprocess;

import com.caixadesapato.api.controller.FotoController;
import com.caixadesapato.api.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import com.caixadesapato.api.service.EmailService;
import com.caixadesapato.api.service.FotoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.io.IOException;

class FotoControllerTest {

    @Mock
    private FotoService fotoService;

    @Mock
    private EmailService emailService;

    @Mock
    private CaixaService caixaService;

    @InjectMocks
    private FotoController fotoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandleFileUploadToEmail() throws IOException {
        MultipartFile mockFile = mock(MultipartFile.class);
        String mockEmail = "test@example.com";
        String flaskResponse = "Imagem recebida pelo Flask";
        Integer mockCaixaId = 1;
        Integer novoStatus = 2;

        when(mockFile.isEmpty()).thenReturn(false);
        when(fotoService.sendToFlask(mockFile)).thenReturn(flaskResponse);

        ResponseEntity<String> response = fotoController.handleFileUploadToEmail(mockFile, mockEmail);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Email Enviado", response.getBody());

        verify(caixaService, times(1)).statusChange(mockCaixaId, novoStatus);
        verify(emailService, times(1)).sendMail(eq(mockEmail), eq("Imagem enviada"), eq("Veja a imagem anexada."), eq(mockFile));
    }
}
