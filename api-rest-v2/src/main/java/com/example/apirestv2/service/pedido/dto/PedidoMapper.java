package com.example.apirestv2.service.pedido.dto;

import com.example.apirestv2.domain.pedido.Pedido;
import java.util.List;

public class PedidoMapper {
    public static PedidoListagemDTO toDTO(Pedido pedido){
        PedidoListagemDTO dto = new PedidoListagemDTO();
        dto.setId(pedido.getId());
        dto.setDataPedido(pedido.getDataPedido());
        dto.setIdDoador(pedido.getIdDoador());
        dto.setStatusPedido(pedido.getStatusPedido());
        dto.setValorTotal(pedido.getValorTotal());
        return dto;
    }

    public static Pedido toEntity(PedidoCriacaoDTO dto){
        Pedido pedido = new Pedido();
        pedido.setStatusPedido(dto.getStatusPedido());
        pedido.setValorTotal(dto.getValorTotal());
        pedido.setIdDoador(dto.getIdDoador());
        return pedido;
    }

    public static List<PedidoListagemDTO> toDTO(List<Pedido> pedidos){
        return pedidos.stream().map(PedidoMapper::toDTO).toList();
    }
}
