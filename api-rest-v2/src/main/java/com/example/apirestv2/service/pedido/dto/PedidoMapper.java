package com.example.apirestv2.service.pedido.dto;

import com.example.apirestv2.domain.caixa.Caixa;
import com.example.apirestv2.domain.pedido.Pedido;

import java.util.ArrayList;
import java.util.List;

public class PedidoMapper {

    public static List<PedidoListagemDTO> toDTO(List<Pedido> pedidos){
        return pedidos.stream().map(PedidoMapper::toDTO).toList();
    }

    public static PedidoListagemDTO toDTO(Pedido pedido){
        PedidoListagemDTO dto = new PedidoListagemDTO();

        dto.setId(pedido.getId());
        dto.setDataPedido(pedido.getDataPedido());
        dto.setIdDoador(pedido.getIdDoador());
        dto.setStatusPedido(pedido.getStatusPedido());
        dto.setValorTotal(pedido.getValorTotal());
        dto.setCaixas(
                toListaCaixasDoPedidoDTO(pedido.getCaixas())
        );

        return dto;
    }

    public static List<PedidoListagemDTO.CaixaDTO> toListaCaixasDoPedidoDTO(List<Caixa> caixas){
        return caixas.stream()
                    .map(PedidoMapper::toCaixaDoPedidoDTO)
                    .toList();
    }

    public static PedidoListagemDTO.CaixaDTO toCaixaDoPedidoDTO(Caixa caixa){
        PedidoListagemDTO.CaixaDTO caixaDto = new PedidoListagemDTO.CaixaDTO();
        caixaDto.setId(caixa.getId());
        caixaDto.setDataEntrega(caixa.getDataEntrega());
        caixaDto.setQuantidade(caixa.getQuantidade());
        return caixaDto;
    }

    public static Pedido toEntity(PedidoCriacaoDTO dto){
        Pedido pedido = new Pedido();
        pedido.setStatusPedido(dto.getStatusPedido());
        pedido.setValorTotal(dto.getValorTotal());
        pedido.setIdDoador(dto.getIdDoador());
        pedido.setCaixas(new ArrayList<>());
        return pedido;
    }

}
