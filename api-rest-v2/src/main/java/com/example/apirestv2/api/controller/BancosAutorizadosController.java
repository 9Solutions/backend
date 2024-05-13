package com.example.apirestv2.api.controller;

import com.example.apirestv2.service.bancoAutorizado.BancoAutorizadoService;
import com.example.apirestv2.service.bancoAutorizado.dto.BancoAutorizadoExternoDTO;
import com.example.apirestv2.service.bancoAutorizado.dto.BancoAutorizadoListagemDTO;
import com.example.apirestv2.service.bancoAutorizado.dto.BancoAutorizadoMapper;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bancos-autorizados")
@RequiredArgsConstructor
public class BancosAutorizadosController {

    private final BancoAutorizadoService bancoAutorizadoService;

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listando os bancos"),
            @ApiResponse(responseCode = "204", description = "Nenhuma banco cadastrado"),
    })
    public ResponseEntity<List<BancoAutorizadoListagemDTO>> listar() {
        List<BancoAutorizadoExternoDTO> externoDTO = bancoAutorizadoService.fetchAll();

        if (externoDTO == null){
            return ResponseEntity.notFound().build();
        }

        List<BancoAutorizadoListagemDTO> dto = BancoAutorizadoMapper.toListagem(externoDTO);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/ordenado-nome")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listando os bancos ordenados por nome"),
            @ApiResponse(responseCode = "204", description = "Nenhuma banco cadastrado"),
    })
    public ResponseEntity<List<BancoAutorizadoListagemDTO>> listarOrdenadoPorNome() {
        List<BancoAutorizadoExternoDTO> externoDTO = bancoAutorizadoService.fetchAll();

        if (externoDTO == null){
            return ResponseEntity.notFound().build();
        }

        List<BancoAutorizadoListagemDTO> dto = BancoAutorizadoMapper.toListagem(externoDTO);
        List<BancoAutorizadoListagemDTO> listaOrdenada = bancoAutorizadoService.ordenacaoPorNome(dto);

        return ResponseEntity.ok(listaOrdenada);
    }

    @GetMapping("/pesquisar-nome/{nome}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listando banco com nome especifico"),
            @ApiResponse(responseCode = "204", description = "Nenhuma banco cadastrado"),
    })
    public ResponseEntity<BancoAutorizadoListagemDTO> listarOrdenadoPorNome(@PathVariable String nome) {
        List<BancoAutorizadoExternoDTO> externoDTO = bancoAutorizadoService.fetchAll();

        if (externoDTO == null){
            return ResponseEntity.notFound().build();
        }

        List<BancoAutorizadoListagemDTO> dto = BancoAutorizadoMapper.toListagem(externoDTO);
        BancoAutorizadoListagemDTO pesquisa = bancoAutorizadoService.pesquisarPorNome(dto, nome);

        return ResponseEntity.ok(pesquisa);
    }
}
