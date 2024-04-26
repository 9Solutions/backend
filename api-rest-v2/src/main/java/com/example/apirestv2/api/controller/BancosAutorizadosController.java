package com.example.apirestv2.api.controller;

import com.example.apirestv2.service.bancoAutorizado.BancoAutorizadoService;
import com.example.apirestv2.service.bancoAutorizado.dto.BancoAutorizadoExternoDTO;
import com.example.apirestv2.service.bancoAutorizado.dto.BancoAutorizadoListagemDTO;
import com.example.apirestv2.service.bancoAutorizado.dto.BancoAutorizadoMapper;
import com.example.apirestv2.utils.ListaObj;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@RequestMapping("/bancos-autorizados")
@RequiredArgsConstructor
public class BancosAutorizadosController {

    private final BancoAutorizadoService bancoAutorizadoService;

    @GetMapping
    @ApiResponse(responseCode = "200", description = "Lista de bancos")
    public ResponseEntity<List<BancoAutorizadoListagemDTO>> listar() {
        List<BancoAutorizadoExternoDTO> externoDTO = bancoAutorizadoService.fetchAll();

        if (externoDTO == null){
            return ResponseEntity.notFound().build();
        }

        List<BancoAutorizadoListagemDTO> dto = BancoAutorizadoMapper.toListagem(externoDTO);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/ordenado-nome")
    @ApiResponse(responseCode = "200", description = "Lista de bancos ordenados por nome")
    public ResponseEntity<List<BancoAutorizadoListagemDTO>> listarOrdenadoPorNome() {
        List<BancoAutorizadoExternoDTO> externoDTO = bancoAutorizadoService.fetchAll();

        if (externoDTO == null){
            return ResponseEntity.notFound().build();
        }

        List<BancoAutorizadoListagemDTO> dto = BancoAutorizadoMapper.toListagem(externoDTO);
        List<BancoAutorizadoListagemDTO> listaOrdenada = bancoAutorizadoService.ordenacaoPorNome(dto);

        return ResponseEntity.ok(listaOrdenada);
    }
}
