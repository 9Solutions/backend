package com.caixadesapato.api.controller;

import com.caixadesapato.api.dto.caixa.CaixaCriacaoDTO;
import com.caixadesapato.api.dto.caixa.CaixaListagemDTO;
import com.caixadesapato.api.dto.caixa.CaixaMapper;
import com.caixadesapato.api.dto.caixa.CaixaUpdateDTO;
import com.caixadesapato.api.model.Caixa;
import com.caixadesapato.api.service.CaixaService;
import com.caixadesapato.api.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/caixas")
@RequiredArgsConstructor
public class CaixaController {

    private final CaixaService service;

    @PostMapping("/import")
    public ResponseEntity<String> importCaixas(@RequestParam("file") MultipartFile file) {
        try {
            List<CaixaCriacaoDTO> caixas = service.processarArquivo(file);
            for (CaixaCriacaoDTO caixa : caixas) {
                Caixa caixaNova = CaixaMapper.toEntity(caixa);
                service.save(caixaNova, caixa.getItensCaixa(), caixa.getIdPedido(), caixa.getIdFaixaEtaria());
            }

            return ResponseEntity.ok("Importação concluída com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro na importação: " + e.getMessage());
        }
    }

    @Operation(summary = "Listar caixas ", description = "Listar todas caixas", tags = "Caixas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listar caixas"),
            @ApiResponse(responseCode = "204", description = "Lista vazia"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping
    public ResponseEntity<List<CaixaListagemDTO>> listAll() {
        List<Caixa> caixas = service.listAll();
        if (caixas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<CaixaListagemDTO> caixasDTO = CaixaMapper.toDTO(caixas);
        return ResponseEntity.ok(caixasDTO);
    }


    @Operation(summary = "Listar dados de uma caixa ", description = "Listar dados de uma caixa pelo ID", tags = "Caixas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listar dados da caixa"),
            @ApiResponse(responseCode = "404", description = "Caixa não encontrada"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CaixaListagemDTO> listByID(
            @PathVariable Integer id
    ) {
        Caixa caixaPorID = service.listByID(id);
        CaixaListagemDTO caixaDTO = CaixaMapper.toDTO(caixaPorID);
        return ResponseEntity.ok(caixaDTO);
    }

    @Operation(summary = "Listar dados de uma caixa ", description = "Listar dados de uma caixa pelo ID", tags = "Caixas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listar dados da caixa"),
            @ApiResponse(responseCode = "404", description = "Caixa não encontrada"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/buscar-por-qrcode/{qrCodeToken}")
    public ResponseEntity<CaixaListagemDTO> findByQrCodeToken(
            @PathVariable String qrCodeToken
    ) {
        Caixa caixaPorQrCodeToken = service.findByQrCodeToken(qrCodeToken);
        CaixaListagemDTO caixaDTO = CaixaMapper.toDTO(caixaPorQrCodeToken);
        return ResponseEntity.ok(caixaDTO);
    }

    @Operation(summary = "Cadastrar uma caixa ", description = "Método responsável por cadastrar uma nova caixa", tags = "Caixas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Caixa cadastrada"),
            @ApiResponse(responseCode = "400", description = "Atributo inválido"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping
    public ResponseEntity<CaixaListagemDTO> create(
            @RequestBody @Valid CaixaCriacaoDTO novaCaixa
    ) {
        Caixa caixa = CaixaMapper.toEntity(novaCaixa);
        Caixa caixaSalva = service.save(
                caixa, novaCaixa.getItensCaixa(), novaCaixa.getIdPedido(), novaCaixa.getIdFaixaEtaria()
        );
        CaixaListagemDTO caixaDTO = CaixaMapper.toDTO(caixaSalva);
        return ResponseEntity.created(null).body(caixaDTO);
    }


    @Operation(summary = "Atualizar uma caixa ", description = "Método responsável por atualizar dados de uma caixa", tags = "Caixas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Caixa atualizada"),
            @ApiResponse(responseCode = "400", description = "Atributo inválido"),
            @ApiResponse(responseCode = "404", description = "Caixa não encontrada"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CaixaListagemDTO> update(
            @PathVariable Integer id,
            @RequestBody @Valid CaixaUpdateDTO novosDados
    ) {
        Caixa caixaAtualizada = service.update(id, novosDados);
        CaixaListagemDTO caixaDTO = CaixaMapper.toDTO(caixaAtualizada);
        return ResponseEntity.ok(caixaDTO);
    }

    @PatchMapping
    public ResponseEntity<Void> statusChange(
            @RequestParam Integer idCaixa,
            @RequestParam Integer status
    ) {
        service.statusChange(idCaixa, status);
        return ResponseEntity.ok(null);
    }

}
