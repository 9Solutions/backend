package com.example.apirestv2.api.controller;

import com.example.apirestv2.domain.pedido.Pedido;
import com.example.apirestv2.service.pedido.PedidoService;
import com.example.apirestv2.service.pedido.dto.PedidoCriacaoDTO;
import com.example.apirestv2.service.pedido.dto.PedidoListagemDTO;
import com.example.apirestv2.service.pedido.dto.PedidoPatchDTO;
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
    public ResponseEntity<List<Pedido>> listAll(){
        return service.listAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoListagemDTO> listById(@PathVariable Integer id){
        return service.listById(id);
    }

    @PostMapping
    public ResponseEntity<PedidoListagemDTO> create(
            @RequestBody @Valid PedidoCriacaoDTO novoPedido
    ) {
        return service.create(novoPedido);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PedidoListagemDTO> statusChange(
            @PathVariable Integer id,
            @RequestBody @Valid PedidoPatchDTO statusChange
            ) {
        return service.statusChange(id, statusChange);
    }

}
