package com.example.apirestv2.api.controller;

import com.example.apirestv2.domain.pedido.Pedido;
import com.example.apirestv2.service.pedido.PedidoService;
import com.example.apirestv2.service.pedido.dto.*;
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

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listando todos os pedidos"),
            @ApiResponse(responseCode = "204", description = "Nenhuma produto cadastrado"),
    })
    public ResponseEntity<List<PedidoListagemSimplesDTO>> listAll(){

        List<Pedido> pedidos = service.listAll();
        if(pedidos.isEmpty()) return ResponseEntity.noContent().build();

        List<PedidoListagemSimplesDTO> pedidosDTO = PedidoMapper.toListagemSimplesdDTO(pedidos);
        return ResponseEntity.ok(pedidosDTO);

    }


    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listando o pedido"),
            @ApiResponse(responseCode = "404", description = "Não foi possivel encontrar dados"),
    })
    public ResponseEntity<PedidoListagemSimplesDTO> listById(@PathVariable Integer id){
        Pedido pedido =  service.listById(id);
        PedidoListagemSimplesDTO pedidoDTO = PedidoMapper.toListagemSimplesdDTO(pedido);
        return ResponseEntity.ok(pedidoDTO);
    }


    @GetMapping("/filter-status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listando o pedido"),
            @ApiResponse(responseCode = "404", description = "Não foi possivel encontrar dados"),
    })
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

    @GetMapping("/all-details/{id}")
    public ResponseEntity<PedidoListagemDetalhadaDTO> listAllDetailsById(
            @PathVariable Integer id
    ) {
        Pedido pedidoPorId = service.listById(id);
        PedidoListagemDetalhadaDTO pedidoDTO = PedidoMapper.toListagemDetalhadaDTO(pedidoPorId);
        return ResponseEntity.ok(pedidoDTO);
    }


    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Atributo(s) inválido(s)"),
    })
    public ResponseEntity<PedidoListagemSimplesDTO> create(
            @RequestBody @Valid PedidoCriacaoDTO novoPedido
    ) {
        Pedido pedido = PedidoMapper.toEntity(novoPedido);
        Pedido pedidoCriado = service.create(pedido, novoPedido.getIdDoador());
        PedidoListagemSimplesDTO pedidoDTO = PedidoMapper.toListagemSimplesdDTO(pedidoCriado);
        return ResponseEntity.ok(pedidoDTO);
    }


    @PatchMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Status do pedido atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Atributo(s) inválido(s)"),
            @ApiResponse(responseCode = "404", description = "Não foi possivel encontrar dados"),
    })
    public ResponseEntity<PedidoListagemSimplesDTO> statusChange(
            @PathVariable Integer id,
            @RequestBody @Valid PedidoPatchDTO statusChange
    ) {
        Pedido pedidoAtualizado = service.statusChange(id, statusChange);
        PedidoListagemSimplesDTO pedidoDTO = PedidoMapper.toListagemSimplesdDTO(pedidoAtualizado);
        return ResponseEntity.ok(pedidoDTO);
    }

}
