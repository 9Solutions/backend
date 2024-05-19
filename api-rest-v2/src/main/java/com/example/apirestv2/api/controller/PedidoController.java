package com.example.apirestv2.api.controller;

import com.example.apirestv2.domain.pedido.Pedido;
import com.example.apirestv2.service.pedido.PedidoService;
import com.example.apirestv2.service.pedido.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService service;

    @Operation(summary = "Listar", description = "Método que retorna todos os dados de forma simplificada", tags = "Pedidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pedidos"),
            @ApiResponse(responseCode = "204", description = "Lista de pedidos vazia"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @GetMapping
    public ResponseEntity<List<PedidoListagemSimplesDTO>> listAll(){

        List<Pedido> pedidos = service.listAll();
        if(pedidos.isEmpty()) return ResponseEntity.noContent().build();

        List<PedidoListagemSimplesDTO> pedidosDTO = PedidoMapper.toListagemSimplesdDTO(pedidos);
        return ResponseEntity.ok(pedidosDTO);

    }


    @Operation(summary = "Listar por ID", description = "Método que retorna os dados de um pedido", tags = "Pedidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listar dados do pedido"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<PedidoListagemSimplesDTO> listById(@PathVariable Integer id){
        Pedido pedido =  service.listById(id);
        PedidoListagemSimplesDTO pedidoDTO = PedidoMapper.toListagemSimplesdDTO(pedido);
        return ResponseEntity.ok(pedidoDTO);
    }


    @Operation(summary = "Listar por STATUS do pedido", description = "Método que retorna os dados dos pedidos de forma detalhada", tags = "Pedidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pedidos detalhada"),
            @ApiResponse(responseCode = "204", description = "Lista vazia"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @GetMapping("/filter-status")
    public ResponseEntity<List<PedidoListagemDetalhadaDTO>> listByStatus(
            @RequestParam Integer statusType
    ){
        List<Pedido> pedidos =  service.listByStatus(statusType);
        if(pedidos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        List<PedidoListagemDetalhadaDTO> pedidosDTO = PedidoMapper.toListagemDetalhadaDTO(pedidos);
        return ResponseEntity.ok(pedidosDTO);
    }


    @Operation(summary = "Listar detalhes do pedido", description = "Método que retorna os dados de um pedido de forma detalhada", tags = "Pedidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lsita dados do pedido"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @GetMapping("/all-details/{id}")
    public ResponseEntity<PedidoListagemDetalhadaDTO> listAllDetailsById(
            @PathVariable Integer id
    ) {
        Pedido pedidoPorId = service.listById(id);
        PedidoListagemDetalhadaDTO pedidoDTO = PedidoMapper.toListagemDetalhadaDTO(pedidoPorId);
        return ResponseEntity.ok(pedidoDTO);
    }


    @Operation(summary = "Criar um novo pedido", description = "Método responsável por criar um novo pedido", tags = "Pedidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Atributo inválido"),
            @ApiResponse(responseCode = "404", description = "Doador não encontrado"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @PostMapping
    public ResponseEntity<PedidoListagemSimplesDTO> create(
            @RequestBody @Valid PedidoCriacaoDTO novoPedido
    ) {
        Pedido pedido = PedidoMapper.toEntity(novoPedido);
        Pedido pedidoCriado = service.create(pedido, novoPedido.getIdDoador());
        PedidoListagemSimplesDTO pedidoDTO = PedidoMapper.toListagemSimplesdDTO(pedidoCriado);
        return ResponseEntity.ok(pedidoDTO);
    }


    @Operation(summary = "Alterar STATUS do pedido", description = "Método responsável por alterar o STATUS de um pedido", tags = "Pedidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Status do pedido atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Atributo inválido", useReturnTypeSchema = false),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @PatchMapping("/{id}")
    public ResponseEntity<PedidoListagemSimplesDTO> statusChange(
            @PathVariable Integer id,
            @RequestBody @Valid PedidoPatchDTO statusChange
    ) {
        Pedido pedidoAtualizado = service.statusChange(id, statusChange);
        PedidoListagemSimplesDTO pedidoDTO = PedidoMapper.toListagemSimplesdDTO(pedidoAtualizado);
        return ResponseEntity.ok(pedidoDTO);
    }

}
