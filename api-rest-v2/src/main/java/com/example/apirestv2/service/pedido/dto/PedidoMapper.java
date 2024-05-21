package com.example.apirestv2.service.pedido.dto;

import com.example.apirestv2.domain.caixa.Caixa;
import com.example.apirestv2.domain.itemCaixa.ItemCaixa;
import com.example.apirestv2.domain.pedido.Pedido;

import java.util.ArrayList;
import java.util.List;

public class PedidoMapper {

    public static List<PedidoListagemSimplesDTO> toListagemSimplesdDTO(List<Pedido> pedidos) {
        return pedidos.stream().map(PedidoMapper::toListagemSimplesdDTO).toList();
    }

    public static PedidoListagemSimplesDTO toListagemSimplesdDTO(Pedido pedido) {
        PedidoListagemSimplesDTO dto = new PedidoListagemSimplesDTO();
        dto.setId(pedido.getId());
        dto.setDataPedido(pedido.getDataPedido());
        dto.setStatusPedido(pedido.getStatusPedido());
        dto.setValorTotal(pedido.getValorTotal());

        PedidoListagemSimplesDTO.DoadorDTO doadorPedidoDTO = new PedidoListagemSimplesDTO.DoadorDTO();
        doadorPedidoDTO.setId(pedido.getDoador().getId());
        doadorPedidoDTO.setNome(pedido.getDoador().getNomeCompleto());
        doadorPedidoDTO.setTelefone(pedido.getDoador().getTelefone());
        dto.setDoador(doadorPedidoDTO);
        return dto;
    }


    public static List<PedidoListagemDetalhadaDTO> toListagemDetalhadaDTO(List<Pedido> pedidos) {
        return pedidos.stream().map(PedidoMapper::toListagemDetalhadaDTO).toList();
    }

    public static PedidoListagemDetalhadaDTO toListagemDetalhadaDTO(Pedido pedido) {

        PedidoListagemDetalhadaDTO pedidoListagemPDF = new PedidoListagemDetalhadaDTO();
        pedidoListagemPDF.setId(pedido.getId());

        PedidoListagemDetalhadaDTO.DoadorDTO doadorListagemPDF = new PedidoListagemDetalhadaDTO.DoadorDTO();
        doadorListagemPDF.setId(pedido.getDoador().getId());
        doadorListagemPDF.setNome(pedido.getDoador().getNomeCompleto());
        doadorListagemPDF.setTelefone(pedido.getDoador().getTelefone());
        pedidoListagemPDF.setDoador(doadorListagemPDF);

        pedidoListagemPDF.setCaixas(
                toListaCaixasDoPedidoDTO(pedido.getCaixas())
        );

        return pedidoListagemPDF;
    }

    public static List<PedidoListagemDetalhadaDTO.CaixaDTO> toListaCaixasDoPedidoDTO(List<Caixa> caixas) {
        return caixas.stream()
                .map(PedidoMapper::toCaixaDoPedidoDTO)
                .toList();
    }

    public static PedidoListagemDetalhadaDTO.CaixaDTO toCaixaDoPedidoDTO(Caixa caixa) {
        PedidoListagemDetalhadaDTO.CaixaDTO caixaDto = new PedidoListagemDetalhadaDTO.CaixaDTO();
        caixaDto.setId(caixa.getId());
        caixaDto.setCarta(caixa.getCarta());
        caixaDto.setUrl(caixa.getUrl());
        caixaDto.setQuantidade(caixa.getQuantidade());
        caixaDto.setFaixaEtaria(caixa.getFaixaEtaria());
        caixaDto.setGenero(caixa.getGenero());
        caixaDto.setItens(
                toItensCaixaDTO(caixa.getItens())
        );
        return caixaDto;
    }

    public static List<PedidoListagemDetalhadaDTO.ItemCaixaDTO> toItensCaixaDTO(List<ItemCaixa> itens) {
        List<PedidoListagemDetalhadaDTO.ItemCaixaDTO> itensDTO = new ArrayList<>();
        for (ItemCaixa itemDaVez : itens) {
            PedidoListagemDetalhadaDTO.ItemCaixaDTO item = new PedidoListagemDetalhadaDTO.ItemCaixaDTO();
            item.setNome(itemDaVez.getProduto().getNome());
            item.setCategoriaProduto(itemDaVez.getProduto());
            itensDTO.add(item);
        }
        return itensDTO;
    }

    public static Pedido toEntity(PedidoCriacaoDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setStatusPedido(dto.getStatusPedido());
        pedido.setValorTotal(dto.getValorTotal());
        pedido.setCaixas(new ArrayList<>());
        return pedido;
    }

}
