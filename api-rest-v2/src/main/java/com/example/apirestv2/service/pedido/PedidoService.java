package com.example.apirestv2.service.pedido;

import com.example.apirestv2.domain.pedido.Pedido;
import com.example.apirestv2.domain.pedido.repository.PedidoRepository;
import com.example.apirestv2.service.pedido.dto.PedidoCriacaoDTO;
import com.example.apirestv2.service.pedido.dto.PedidoListagemDTO;
import com.example.apirestv2.service.pedido.dto.PedidoMapper;
import com.example.apirestv2.service.pedido.dto.PedidoPatchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository action;

    public ResponseEntity<List<Pedido>> listAll(){
        List<Pedido> pedidos = action.findAll();
        if(pedidos.isEmpty()){
            return ResponseEntity.noContent().build();
        }

//        List<PedidoListagemDTO> pedidosDTO = PedidoMapper.toDTO(pedidos);
        return ResponseEntity.ok(pedidos);
    }

    public ResponseEntity<PedidoListagemDTO> listById(Integer id){
        Optional<Pedido> pedido = action.findById(id);
        if(pedido.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        PedidoListagemDTO pedidoDTO = PedidoMapper.toDTO(pedido.get());
        return ResponseEntity.ok(pedidoDTO);
    }

    public ResponseEntity<PedidoListagemDTO> create(PedidoCriacaoDTO novoPedido){

        Pedido pedido = PedidoMapper.toEntity(novoPedido);
        Pedido pedidoSalvo = action.save(pedido);
        PedidoListagemDTO pedidoDTO = PedidoMapper.toDTO(pedidoSalvo);
        return ResponseEntity.created(null).body(pedidoDTO);

    }

    public ResponseEntity<PedidoListagemDTO> statusChange(Integer id, PedidoPatchDTO change){

        Optional<Pedido> pedido = action.findById(id);
        if(pedido.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        pedido.get().setStatusPedido(change.getStatusChange());
        Pedido pedidoAtualizado = action.save(pedido.get());
        PedidoListagemDTO pedidoDTO = PedidoMapper.toDTO(pedidoAtualizado);
        return ResponseEntity.created(null).body(pedidoDTO);

    }



}
