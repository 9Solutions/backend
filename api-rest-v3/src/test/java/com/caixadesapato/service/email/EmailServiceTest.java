package com.caixadesapato.service.email;

import com.caixadesapato.api.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;

class EmailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailService emailService;

    @Value("${spring.mail.username}")
    private String remetente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        emailService = new EmailService(javaMailSender);
    }

    /*@Test
    void testSendMail() {
        String destinatario = "destinatario@example.com";
        String assunto = "Assunto de Teste";
        String mensagem = "Mensagem de Teste";

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(remetente);
        mailMessage.setTo(destinatario);
        mailMessage.setSubject(assunto);
        mailMessage.setText(mensagem);

        doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));

        emailService.sendMail(destinatario, assunto, mensagem, null);

        verify(javaMailSender, times(1)).send(mailMessage);
    }

    @Test
    void testSendMailComExcecao() {
        String destinatario = "destinatario@example.com";
        String assunto = "Assunto de Teste";
        String mensagem = "Mensagem de Teste";

        doThrow(new RuntimeException("Erro ao enviar email")).when(javaMailSender).send(any(SimpleMailMessage.class));

        emailService.sendMail(destinatario, assunto, mensagem, null);

        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }*/
}

