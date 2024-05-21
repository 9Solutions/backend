package com.example.apirestv2.api.controller.doador;

import com.example.apirestv2.domain.doador.Doador;
import com.example.apirestv2.domain.doador.repository.DoadorRepository;
import com.example.apirestv2.service.doador.DoadorService;
import com.example.apirestv2.service.doador.autenticacao.dto.DoadorLoginDTO;
import com.example.apirestv2.service.doador.autenticacao.dto.DoadorTokenDTO;
import com.example.apirestv2.service.doador.dto.DoadorAlteracaoSenhaDTO;
import com.example.apirestv2.service.doador.dto.DoadorCriacaoDTO;
import com.example.apirestv2.service.doador.dto.DoadorListagemDTO;
import com.example.apirestv2.service.doador.dto.mapper.DoadorMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.security.Principal;
import java.util.List;

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
    public ResponseEntity<Void> cadastrar(@RequestBody @Valid DoadorCriacaoDTO doadorCriacaoDto) {
        this.doadorService.cadastrar(doadorCriacaoDto);
        return ResponseEntity.status(201).build();
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

    //login e logoff do doador com token
}
