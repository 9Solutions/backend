package com.caixadesapato.api.dto.pedido;

import com.caixadesapato.api.model.*;

import java.util.ArrayList;
import java.util.List;

public class PedidoMapper {

	public static List<PedidoListagemSimplesDTO> toListagemSimplesdDTO(List<Pedido> pedidos) {
		return pedidos.stream().map(PedidoMapper::toListagemSimplesdDTO).toList();
	}

	public static PedidoCriacaoDTO toPedidoCriacaoDTO(PedidoListagemDetalhadaDTO pedidoListagem) {
		if (pedidoListagem == null) {
			return null;
		}

		PedidoCriacaoDTO pedidoCriacao = new PedidoCriacaoDTO();
		pedidoCriacao.setValorTotal(pedidoListagem.getValorTotal());
		pedidoCriacao.setStatusPedido(mapeiaStatusParaInteiro(pedidoListagem.getStatus()));

		if (pedidoListagem.getDoador() != null) {
			pedidoCriacao.setIdDoador(pedidoListagem.getDoador().getId());
		}

		return pedidoCriacao;
	}

	private static Integer mapeiaStatusParaInteiro(String status) {
		switch (status.toLowerCase()) {
			case "pendente":
				return 0;
			case "aprovado":
				return 1;
			case "rejeitado":
				return 2;
			default:
				return null;
		}
	}

	public static List<PedidoCriacaoDTO> toCriacaoDTO(List<PedidoListagemDetalhadaDTO> pedidos) {
		return pedidos.stream().map(PedidoMapper::toPedidoCriacaoDTO).toList();
	}

	public static PedidoListagemSimplesDTO toListagemSimplesdDTO(Pedido pedido) {
		PedidoListagemSimplesDTO dto = new PedidoListagemSimplesDTO();
		dto.setId(pedido.getId());
		dto.setDataPedido(pedido.getDataPedido());
		dto.setValorTotal(pedido.getValorTotal());
		dto.setQuantidadeCaixas(pedido.getCaixas().size());

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
		pedidoListagemPDF.setStatus(pedido.getStatusPedido().getStatus());
		pedidoListagemPDF.setValorTotal(pedido.getValorTotal());

		PedidoListagemDetalhadaDTO.DoadorDTO doadorListagemPDF = new PedidoListagemDetalhadaDTO.DoadorDTO();
		doadorListagemPDF.setId(pedido.getDoador().getId());
		doadorListagemPDF.setNome(pedido.getDoador().getNomeCompleto());
		doadorListagemPDF.setEmail(pedido.getDoador().getEmail());
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
		caixaDto.setQrCodeToken(caixa.getQrCodeToken());
		caixaDto.setItens(
			toItensDaCaixaDTO(caixa.getItens())
		);
		caixaDto.setEtapas(toListEtapasDTO(caixa.getEtapas()));
		return caixaDto;
	}

	public static List<String> toItensDaCaixaDTO(List<ItemCaixa> itens) {
		return itens.stream()
			.map(item -> item.getProduto().getNome())
			.toList();
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

	public static Pedido toEntity(PedidoListagemDetalhadaDTO pedidoListagem) {
		if (pedidoListagem == null) {
			return null; // ou lance uma exceção, dependendo do seu tratamento de erros
		}

		Pedido pedido = new Pedido();
		pedido.setId(pedidoListagem.getId());
		pedido.setValorTotal(pedidoListagem.getValorTotal());

		// Definindo o Doador
		if (pedidoListagem.getDoador() != null) {
			Doador doador = new Doador();
			doador.setId(pedidoListagem.getDoador().getId());
			doador.setNomeCompleto(pedidoListagem.getDoador().getNome());
			doador.setEmail(pedidoListagem.getDoador().getEmail());
			doador.setTelefone(pedidoListagem.getDoador().getTelefone());
			pedido.setDoador(doador);
		}

		// Definindo o StatusPedido
		if (pedidoListagem.getStatus() != null) {
			StatusPedido statusPedido = new StatusPedido();
			statusPedido.setId(mapeiaStatusParaId(pedidoListagem.getStatus())); // Você pode ajustar essa lógica conforme necessário
			pedido.setStatusPedido(statusPedido);
		}

		// Mapeando caixas se necessário (supondo que você tenha um mapper para isso)
		// pedido.setCaixas(CaixaMapper.toEntityList(pedidoListagem.getCaixas()));

		return pedido;
	}

	private static Integer mapeiaStatusParaId(String status) {
		// Aqui você pode implementar a lógica para mapear o status
		// Exemplo:
		switch (status.toLowerCase()) {
			case "pendente":
				return 1; // Id correspondente ao status "pendente"
			case "aprovado":
				return 2; // Id correspondente ao status "aprovado"
			case "rejeitado":
				return 3; // Id correspondente ao status "rejeitado"
			default:
				return null; // ou lance uma exceção se o status não for válido
		}
	}

}
