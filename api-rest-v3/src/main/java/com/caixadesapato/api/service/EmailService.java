package com.caixadesapato.api.service;

import jakarta.mail.util.ByteArrayDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String remetente;

    public void sendMail(String destinatario, String assunto, String mensagem, MultipartFile file) {

        try {
            MimeMessagePreparator preparator;

            if (file != null && !file.isEmpty()) {
                // Cai aqui caso tenha imagem (ou seja, é o envio de foto com qrcode)
                preparator = mimeMessage -> {
                    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                    helper.setFrom(remetente);
                    helper.setTo(destinatario);
                    helper.setSubject(assunto);
                    helper.setText(mensagem, true);
                    ByteArrayDataSource dataSource = new ByteArrayDataSource(file.getBytes(), file.getContentType());
                    helper.addAttachment("Foto da Entrega", dataSource);
                };
            } else {
                // Cai aqui caso não tenha imagem (ou seja, só alterou o status)
                preparator = mimeMessage -> {
                    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                    helper.setFrom(remetente);
                    helper.setTo(destinatario);
                    helper.setSubject(assunto);
                    helper.setText(mensagem, true);
                };
            }

            javaMailSender.send(preparator);

        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
