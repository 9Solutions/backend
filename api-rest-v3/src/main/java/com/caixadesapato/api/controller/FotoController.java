package com.caixadesapato.api.controller;

import com.caixadesapato.api.service.EmailService;
import com.caixadesapato.api.service.FotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/fotos")
@RequiredArgsConstructor
public class FotoController {

    private final FotoService fotoService;
    private final EmailService emailService;

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("image") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("No file selected", HttpStatus.BAD_REQUEST);
        }

        try {
            // Send the image to Flask
            String flaskResponse = fotoService.sendToFlask(file);
            return new ResponseEntity<>(flaskResponse, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to send image", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/upload-email")
    public ResponseEntity<String> handleFileUploadToEmail(@RequestParam("image") MultipartFile file, @RequestParam("email") String email) {
        String assunto = "Imagem enviada";
        String mensagem = "Veja a imagem anexada.";

        emailService.sendMail(email, assunto, mensagem, file);

        return new ResponseEntity<>("Email Enviado", HttpStatus.OK);
    }
}
