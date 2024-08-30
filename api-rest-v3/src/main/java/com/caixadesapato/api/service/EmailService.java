package com.caixadesapato.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
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
            MimeMessagePreparator preparator = mimeMessage -> {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                helper.setFrom(remetente);
                helper.setTo(destinatario);
                helper.setSubject(assunto);
                helper.setText(mensagem, true);

                if (file != null && !file.isEmpty()) {
                    helper.addAttachment(file.getOriginalFilename(), file);
                }
            };
            javaMailSender.send(preparator);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }

    }
}
