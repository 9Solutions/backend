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
public class    EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String remetente;

    public void sendMail(String destinatario, String assunto, MultipartFile file) {

        String mensagem = "<!DOCTYPE html>\n" +
            "<html lang=\"pt-BR\">\n" +
            "<head>\n" +
            "   <meta charset=\"UTF-8\">\n" +
            "   <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "   <title>Email Projeto Caixa de Sapato</title>\n" +
            "   <style>\n" +
            "       body {\n" +
            "           font-family: Arial, sans-serif;\n" +
            "           margin: 0;\n" +
            "           padding: 0;\n" +
            "           background-color: #f4f4f4;\n" +
            "       }\n" +
            "\n" +
            "       .email-container {\n" +
            "           max-width: 600px;\n" +
            "           margin: 0 auto;\n" +
            "           background-color: #ffffff;\n" +
            "           padding: 20px;\n" +
            "           border-radius: 5px;\n" +
            "       }\n" +
            "\n" +
            "       .email-header {\n" +
            "           text-align: center;\n" +
            "           padding: 20px;\n" +
            "           background-color: #008816;\n" +
            "           color: #ffffff;\n" +
            "           font-size: 24px;\n" +
            "           border-top-left-radius: 5px;\n" +
            "           border-top-right-radius: 5px;\n" +
            "       }\n" +
            "\n" +
            "       .yellow-line {\n" +
            "           height: 5px;\n" +
            "           background-color: #F79A00;\n" +
            "       }\n" +
            "\n" +
            "       .email-body {\n" +
            "           padding: 20px;\n" +
            "           color: #333333;\n" +
            "           line-height: 1.6;\n" +
            "       }\n" +
            "\n" +
            "       .email-body p {\n" +
            "           margin: 10px 0;\n" +
            "       }\n" +
            "\n" +
            "       .email-footer {\n" +
            "           text-align: center;\n" +
            "           padding: 10px;\n" +
            "           font-size: 12px;\n" +
            "           color: #777777;\n" +
            "           border-top: 1px solid #dddddd;\n" +
            "       }\n" +
            "\n" +
            "       .btn {\n" +
            "           display: inline-block;\n" +
            "           padding: 10px 20px;\n" +
            "           color: #ffffff;\n" +
            "           background-color: #008816;\n" +
            "           text-decoration: none;\n" +
            "           border-radius: 5px;\n" +
            "           margin-top: 10px;\n" +
            "       }\n" +
            "   </style>\n" +
            "</head>\n" +
            "<body>\n" +
            "   <div class=\"email-container\">\n" +
            "       <div class=\"email-header\">\n" +
            "           Projeto Caixa de Sapato\n" +
            "           <img src=\"https://static.wixstatic.com/media/ee1977_e673de4fc9064326b1237eccef3571fe~mv2.png/v1/fill/w_375,h_370,al_c,q_85,usm_0.66_1.00_0.01,enc_auto/caixa%20copy_edited.png\" style=\"width: 9%; margin-left: 20px;\" alt=\"\">\n" +
            "       </div>\n" +
            "\n" +
            "       <div class=\"yellow-line\"></div>\n" +
            "\n" +
            "       <div class=\"email-body\">\n" +
            "           <p>Olá, [Nome do Destinatário],</p>\n" +
            "\n" +
            "           <p>Bem-vindo ao nosso serviço de entregas de caixas por todo o Brasil! Estamos muito felizes em tê-lo conosco.</p>\n" +
            "\n" +
            "           <p>Aqui está a criança que você alegrou com o envio da sua caixinha de sapato hoje! \uD83D\uDE00</p>\n" +
            "\n" +
            "           <center>\n" +
            "               <img src=\"https://images.pexels.com/photos/1231215/pexels-photo-1231215.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1\" alt=\"\" style=\"width: 90%;\">\n" +
            "           </center>\n" +
            "\n" +
            "           <p>Se precisar de ajuda, entre em contato conosco respondendo a este e-mail.</p>\n" +
            "\n" +
            "           <p><br>Atenciosamente,<br>\n" +
            "           Equipe de Suporte do Projeto Caixa de Sapato</p>\n" +
            "       </div>\n" +
            "\n" +
            "       <div class=\"email-footer\">\n" +
            "           &copy; 2024 Projeto Caixa de Sapato. Todos os direitos reservados.<br>\n" +
            "           Av. Guido Caloi 1000. Bloco 6 - Térreo - Santo Amaro - SP, 05802-140 Alameda Europa, 88 - 2 Subsolo - Tamboré, Santana de Parnaíba - SP, 06543-325 Organico Coworking - Alameda Grajaú 219. Alphaville Barueri CEP 06454-050 Av. Tucunaré 1192 T 1 - 14. Tamboré - Barueri - 06460020  | atendimento@projetocaixadesapato.org\n" +
            "       </div>\n" +
            "   </div>\n" +
            "</body>\n" +
            "</html>";
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
