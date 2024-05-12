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

    public List<Pedido> listAll(){
        return action.findAll();
    }

    public Pedido listById(Integer id){
        return action.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Não encontrado")
        );
    }

    public Pedido create(PedidoCriacaoDTO novoPedido){
        Pedido pedido = PedidoMapper.toEntity(novoPedido);
        return action.save(pedido);
    }

    public Pedido statusChange(Integer id, PedidoPatchDTO change){

        Optional<Pedido> pedido = action.findById(id);
        if(pedido.isEmpty()){
            throw new IllegalArgumentException("Não encontrado");
        }

        pedido.get().setStatusPedido(change.getStatusChange());
        return action.save(pedido.get());

    }



}
