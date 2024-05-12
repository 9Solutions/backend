package com.example.apirestv2.api.controller;

import com.example.apirestv2.domain.pedido.Pedido;
import com.example.apirestv2.service.pedido.PedidoService;
import com.example.apirestv2.service.pedido.dto.PedidoCriacaoDTO;
import com.example.apirestv2.service.pedido.dto.PedidoListagemDTO;
import com.example.apirestv2.service.pedido.dto.PedidoMapper;
import com.example.apirestv2.service.pedido.dto.PedidoPatchDTO;
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
    public ResponseEntity<List<PedidoListagemDTO>> listAll(){

        List<Pedido> pedidos = service.listAll();
        if(pedidos.isEmpty()) return ResponseEntity.noContent().build();

        List<PedidoListagemDTO> pedidosDTO = PedidoMapper.toDTO(pedidos);
        return ResponseEntity.ok(pedidosDTO);

    }


    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listando o pedido"),
            @ApiResponse(responseCode = "404", description = "Não foi possivel encontrar dados"),
    })
    public ResponseEntity<PedidoListagemDTO> listById(@PathVariable Integer id){
        Pedido pedido =  service.listById(id);
        PedidoListagemDTO pedidoDTO = PedidoMapper.toDTO(pedido);
        return ResponseEntity.ok(pedidoDTO);
    }


    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Atributo(s) inválido(s)"),
    })
    public ResponseEntity<PedidoListagemDTO> create(
            @RequestBody @Valid PedidoCriacaoDTO novoPedido
    ) {
        Pedido pedidoCriado = service.create(novoPedido);
        PedidoListagemDTO pedidoDTO = PedidoMapper.toDTO(pedidoCriado);
        return ResponseEntity.ok(pedidoDTO);
    }


    @PatchMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Status do pedido atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Atributo(s) inválido(s)"),
            @ApiResponse(responseCode = "404", description = "Não foi possivel encontrar dados"),
    })
    public ResponseEntity<PedidoListagemDTO> statusChange(
            @PathVariable Integer id,
            @RequestBody @Valid PedidoPatchDTO statusChange
    ) {
        Pedido pedidoAtualizado = service.statusChange(id, statusChange);
        PedidoListagemDTO pedidoDTO = PedidoMapper.toDTO(pedidoAtualizado);
        return ResponseEntity.ok(pedidoDTO);
    }

}
