package com.caixadesapato.api.controller;

import com.caixadesapato.api.dto.doador.*;
import com.caixadesapato.api.model.Doador;
import com.caixadesapato.api.service.DoadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/doadores")
@RequiredArgsConstructor
public class DoadorController {

    private final DoadorService doadorService;

    //cadastro do doador
    @PostMapping
    @Operation(summary = "Cadastrar doador", description = "Método responsável por cadastrar um novo doador", tags = "Doadores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Doador cadastrado"),
            @ApiResponse(responseCode = "204", description = "Nenhuma caixa cadastrada"),
    })
    public ResponseEntity<DoadorListagemDTO> cadastrar(@RequestBody @Valid DoadorCriacaoDTO doadorCriacaoDto) {
        Doador doador = this.doadorService.cadastrar(doadorCriacaoDto);
        DoadorListagemDTO doadorListagemDto = DoadorMapper.toDto(doador);
        return ResponseEntity.status(201).body(doadorListagemDto);
    }

    //login do doador
    @Operation(summary = "Login doador", description = "Método responsável realziar o LOGIN", tags = "Doadores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado"),
            @ApiResponse(responseCode = "401", description = "Token inválido"),
            @ApiResponse(responseCode = "404", description = "Email do doador não cadastrado"),
    })
    @PostMapping("/login")
    public ResponseEntity<DoadorTokenDTO> login(@RequestBody DoadorLoginDTO doadorLoginDTO) {
        DoadorTokenDTO doadorTokenDto = this.doadorService.login(doadorLoginDTO);
        return ResponseEntity.status(200).body(doadorTokenDto);
    }

    @PatchMapping("/alterar-senha")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Listar doadores", description = "Método responsável por alterar senha do usuário", tags = "Doadores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Senha alterada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Token inválido"),
            @ApiResponse(responseCode = "404", description = "Email do doador não cadastrado"),
    })
    public ResponseEntity<String> alterarSenha(@RequestBody DoadorAlteracaoSenhaDTO alterarSenhaDTO, Principal principal) {
        try {
            String email = principal.getName();
            doadorService.alterarSenha(email, alterarSenhaDTO);
            return ResponseEntity.ok("Senha alterada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
