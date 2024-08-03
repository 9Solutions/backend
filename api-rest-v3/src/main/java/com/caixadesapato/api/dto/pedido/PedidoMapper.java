package com.caixadesapato.api.dto.pedido;

import com.caixadesapato.api.model.*;

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
        dto.setValorTotal(pedido.getValorTotal());

        PedidoListagemSimplesDTO.StatusPedidoDTO statusPedidoDTO = new PedidoListagemSimplesDTO.StatusPedidoDTO();
        statusPedidoDTO.setId(pedido.getStatusPedido().getId());
        statusPedidoDTO.setStatus(pedido.getStatusPedido().getStatus());
        dto.setStatusPedido(statusPedidoDTO);

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
        caixaDto.setFaixaEtaria(caixa.getFaixaEtaria().getFaixaNome());
        caixaDto.setGenero(caixa.getGenero());
        caixaDto.setItens(
                toItensDaCaixaDTO(caixa.getItens())
        );
        caixaDto.setEtapas(toListEtapasDTO(caixa.getEtapas()));
        return caixaDto;
    }

    public static List<PedidoListagemDetalhadaDTO.ItemCaixaDTO> toItensDaCaixaDTO(List<ItemCaixa> itens) {
        return itens.stream().map(PedidoMapper::toItensDaCaixaDTO).toList();
    }

    public static PedidoListagemDetalhadaDTO.ItemCaixaDTO toItensDaCaixaDTO(ItemCaixa item){
        PedidoListagemDetalhadaDTO.ItemCaixaDTO itens = new PedidoListagemDetalhadaDTO.ItemCaixaDTO();
        itens.setNome(item.getProduto().getNome());
        return itens;
    }

    public static List<PedidoListagemDetalhadaDTO.EtapaCaixaDTO> toListEtapasDTO(List<EtapaCaixa> etapas) {
        List<PedidoListagemDetalhadaDTO.EtapaCaixaDTO> etapasDTO = new ArrayList<>();
        for (EtapaCaixa etapaDaVez : etapas) {
            PedidoListagemDetalhadaDTO.EtapaCaixaDTO etapaDTO = new PedidoListagemDetalhadaDTO.EtapaCaixaDTO();
            etapaDTO.setStatus(etapaDaVez.getStatus().getStatus());
            etapaDTO.setUpdate(etapaDaVez.getUpdateAt());
            etapasDTO.add(etapaDTO);
        }
        return etapasDTO;
    }

    public static Pedido toEntity(PedidoCriacaoDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setValorTotal(dto.getValorTotal());
        pedido.setCaixas(new ArrayList<>());

        StatusPedido statusPedido = new StatusPedido();
        statusPedido.setId(dto.getStatusPedido());
        pedido.setStatusPedido(statusPedido);
        return pedido;
    }

}
