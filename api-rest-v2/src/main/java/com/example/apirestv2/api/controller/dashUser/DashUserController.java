package com.example.apirestv2.api.controller.dashUser;

import com.example.apirestv2.service.doador.DoadorService;
import com.example.apirestv2.service.doador.autenticacao.dto.DoadorLoginDTO;
import com.example.apirestv2.service.doador.autenticacao.dto.DoadorTokenDTO;
import com.example.apirestv2.service.doador.dto.DoadorAlteracaoSenhaDTO;
import com.example.apirestv2.service.doador.dto.DoadorCriacaoDTO;
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
@RequestMapping("/dashUsers")
@RequiredArgsConstructor
public class DashUserController {
    private final DashUserService dashUserService;

    @PostMapping
    @Operation(summary = "Cadastrar Usuário da Dashboard", description = "Método responsável por cadastrar um novo usuário da Dashboard", tags = "Usuários da Dashboard")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário da Dashboard cadastrado"),
    })
    public ResponseEntity<Void> cadastrar(@RequestBody @Valid DoadorCriacaoDTO doadorCriacaoDto) {
        this.dashUserService.cadastrar(doadorCriacaoDto);
        return ResponseEntity.status(201).build();
    }

    //login do doador
    @Operation(summary = "Login do Usuário da Dashboard", description = "Método responsável realizar o LOGIN", tags = "Usuários da Dashboard")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado"),
            @ApiResponse(responseCode = "401", description = "Token inválido"),
            @ApiResponse(responseCode = "404", description = "Email do usuário não cadastrado"),
    })
    @PostMapping("/login")
    public ResponseEntity<DoadorTokenDTO> login(@RequestBody DoadorLoginDTO doadorLoginDTO) {
        DoadorTokenDTO doadorTokenDto = this.dashUserService.login(doadorLoginDTO);

        return ResponseEntity.status(200).body(doadorTokenDto);
    }

    @PatchMapping("/alterar-senha")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Listar usuários da Dashboard", description = "Método responsável por alterar senha do usuário da dashboard", tags = "Usuários da Dashboard")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Senha alterada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Token inválido"),
            @ApiResponse(responseCode = "404", description = "Email do usuário não cadastrado"),
    })
    public ResponseEntity<String> alterarSenha(@RequestBody DoadorAlteracaoSenhaDTO alterarSenhaDTO, Principal principal) {
        try {
            String email = principal.getName();
            dashUserService.alterarSenha(email, alterarSenhaDTO);
            return ResponseEntity.ok("Senha alterada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
