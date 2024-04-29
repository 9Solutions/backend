package com.example.apirestv2.api.controller.doador;

import com.example.apirestv2.domain.doador.Doador;
import com.example.apirestv2.domain.doador.repository.DoadorRepository;
import com.example.apirestv2.service.doador.DoadorService;
import com.example.apirestv2.service.doador.autenticacao.dto.DoadorLoginDTO;
import com.example.apirestv2.service.doador.autenticacao.dto.DoadorTokenDTO;
import com.example.apirestv2.service.doador.dto.DoadorCriacaoDTO;
import com.example.apirestv2.service.doador.dto.DoadorListagemDTO;
import com.example.apirestv2.service.doador.dto.mapper.DoadorMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@RestController
@RequestMapping("/doadores")
@RequiredArgsConstructor
public class DoadorController {
    private final DoadorService doadorService;

//    @GetMapping
//    public ResponseEntity<List<DoadorListagemDTO>> listar() {
//        List<Doador> doadores = doadorRepository.findAll();
//
//        if (doadores.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        }
//
//        return ResponseEntity.ok(DoadorMapper.toDto(doadores));
//    }

    //cadastro do doador
    @PostMapping
    @SecurityRequirement(name = "Bearer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Doador cadastrado com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhuma caixa cadastrada"),
    })
    public ResponseEntity<Void> cadastrar(@RequestBody @Valid DoadorCriacaoDTO doadorCriacaoDto) {
        this.doadorService.cadastrar(doadorCriacaoDto);
        return ResponseEntity.status(201).build();
    }

    //login do doador
    @PostMapping("/login")@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado"),
            @ApiResponse(responseCode = "401", description = "Token inválido"),
            @ApiResponse(responseCode = "404", description = "Email do doador não cadastrado"),
    })
    public ResponseEntity<DoadorTokenDTO> login(@RequestBody DoadorLoginDTO doadorLoginDTO) {
        DoadorTokenDTO doadorTokenDto = this.doadorService.login(doadorLoginDTO);

        return ResponseEntity.status(200).body(doadorTokenDto);
    }

    //atualizacao de senha do doador

    //login e logoff do doador com token


}
