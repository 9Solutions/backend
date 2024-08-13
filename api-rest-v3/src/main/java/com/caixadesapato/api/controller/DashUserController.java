package com.caixadesapato.api.controller;

import com.caixadesapato.api.dto.dash.DashUserAlteracaoSenhaDTO;
import com.caixadesapato.api.dto.dash.DashUserCriacaoDTO;
import com.caixadesapato.api.dto.dash.DashUserLoginDTO;
import com.caixadesapato.api.dto.dash.DashUserTokenDTO;
import com.caixadesapato.api.model.view.*;
import com.caixadesapato.api.service.DashUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashUserController {

    private final DashUserService dashUserService;
//
//    @PostMapping
//    @Operation(summary = "Cadastrar Usuário da Dashboard", description = "Método responsável por cadastrar um novo usuário da Dashboard", tags = "Usuários da Dashboard")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "Usuário da Dashboard cadastrado"),
//    })
//    public ResponseEntity<Void> cadastrar(@RequestBody @Valid DashUserCriacaoDTO doadorCriacaoDto) {
//        this.dashUserService.cadastrar(doadorCriacaoDto);
//        return ResponseEntity.status(201).build();
//    }
//
//    //login do doador
//    @Operation(summary = "Login do Usuário da Dashboard", description = "Método responsável realizar o LOGIN", tags = "Usuários da Dashboard")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Login realizado"),
//            @ApiResponse(responseCode = "401", description = "Token inválido"),
//            @ApiResponse(responseCode = "404", description = "Email do usuário não cadastrado"),
//    })
//    @PostMapping("/login")
//    public ResponseEntity<DashUserTokenDTO> login(@RequestBody DashUserLoginDTO doadorLoginDTO) {
//        DashUserTokenDTO doadorTokenDto = this.dashUserService.login(doadorLoginDTO);
//
//        return ResponseEntity.status(200).body(doadorTokenDto);
//    }
//
//    @PatchMapping("/alterar-senha")
//    @SecurityRequirement(name = "Bearer")
//    @Operation(summary = "Listar usuários da Dashboard", description = "Método responsável por alterar senha do usuário da dashboard", tags = "Usuários da Dashboard")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Senha alterada com sucesso"),
//            @ApiResponse(responseCode = "401", description = "Token inválido"),
//            @ApiResponse(responseCode = "404", description = "Email do usuário não cadastrado"),
//    })
//    public ResponseEntity<String> alterarSenha(@RequestBody DashUserAlteracaoSenhaDTO alterarSenhaDTO, Principal principal) {
//        try {
//            String email = principal.getName();
//            dashUserService.alterarSenha(email, alterarSenhaDTO);
//            return ResponseEntity.ok("Senha alterada com sucesso!");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

    @Operation(summary = "Retorna a quantidade de caixas atrasadas", tags = "Dashboard")
    @GetMapping("/caixas-atrasadas")
    public List<VwCaixasAtrasadas> caixasAtrasadas() {
        return dashUserService.getCaixasAtrasadas();
    }

    @Operation(summary = "Retorna a quantidade de caixas em montagem", tags = "Dashboard")
    @GetMapping("/caixas-em-montagem")
    public List<VwCaixasEmMontagem> caixasEmMontagems() {
        return dashUserService.getCaixasEmMontagem();
    }

    @Operation(summary = "Retorna a quantidade de caixas para entregar", tags = "Dashboard")
    @GetMapping("/caixas-para-entregar")
    public List<VwCaixasParaEntregar> caixasParaEntregar() {
        return dashUserService.getCaixasParaEntregar();
    }

    @Operation(summary = "Retorna a quantidade de doacoes por mes", tags = "Dashboard")
    @GetMapping("/doacoes-por-mes")
    public List<VwQtdeDoacoesPorMes> doacoesPorMes(
            @RequestParam Integer ano,
            @RequestParam Integer mes
    ) {
        return dashUserService.findByAnoAndMes(ano, mes);
    }

    @Operation(summary = "Retorna a quantidade de pedidos por faixa etaria", tags = "Dashboard")
    @GetMapping("/pedidos-por-faixa/{ano}")
    public List<VwQtdePedidosPorFaixaEtaria> pedidosPorFaixaEtaria(@PathVariable Integer ano) {
        return dashUserService.findByAnoAndMes(ano);
    }

}
