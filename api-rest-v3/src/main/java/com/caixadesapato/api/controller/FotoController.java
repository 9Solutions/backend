package com.caixadesapato.api.controller;

import com.caixadesapato.api.model.Pedido;
import com.caixadesapato.api.service.EmailService;
import com.caixadesapato.api.service.FotoService;
import com.caixadesapato.api.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/fotos")
@RequiredArgsConstructor
public class FotoController {

    private final FotoService fotoService;
    private final EmailService emailService;
    private final PedidoService pedidoService;

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

    //@PostMapping("/upload-email/{id}")
    //public ResponseEntity<String> handleFileUploadToEmail(@RequestParam("image") MultipartFile file, @PathVariable Integer id) {
        //String assunto = "Imagem enviada";
        //String mensagem = "Veja a imagem anexada.";

        //Pedido pedido = pedidoService.listById(id);
        //String email = pedido.getDoador().getEmail();

        //emailService.sendMail(email, assunto, mensagem, file);

       // return new ResponseEntity<>("Email Enviado", HttpStatus.OK);
    //}
}
