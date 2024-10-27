package com.caixadesapato.api.service;

import com.caixadesapato.api.dto.pedido.PedidoListagemDetalhadaDTO;
import com.caixadesapato.api.dto.pedido.PedidoMapper;
import com.caixadesapato.api.dto.pedido.PedidoPatchDTO;
import com.caixadesapato.api.model.Doador;
import com.caixadesapato.api.model.Pedido;
import com.caixadesapato.api.model.StatusPedido;
import com.caixadesapato.api.model.view.VwFiltroPedido;
import com.caixadesapato.api.repository.PedidoRepository;
import com.caixadesapato.api.repository.view.VwFiltrosPedidosRepository;
import com.caixadesapato.api.utils.interfaces.PublisherChange;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PedidoService implements PublisherChange {

	private final PedidoRepository action;
	private final DoadorService doadorService;
	private final StatusPedidoService statusPedidoService;
	private final VwFiltrosPedidosRepository actionFiltro;
	private Hashtable<Integer, Pedido> pedidoTable = new Hashtable<>();

	public void adicionarPedido(Integer id, Pedido pedido) {
		pedidoTable.put(id, pedido);
	}

	public void loadPedidosIntoTable() {
		List<Pedido> allPedidos = action.findAll();

		if (pedidoTable == null) {
			pedidoTable = new Hashtable<>();
		}

		pedidoTable.clear();

		for (Pedido pedido : allPedidos) {
			pedidoTable.put(pedido.getId(), pedido);
		}
	}

	public List<PedidoListagemDetalhadaDTO> listByPage(int page, int pageSize) {
		if (pedidoTable == null || pedidoTable.isEmpty()) {
			System.out.println("Tabela de pedidos vazia ou nula");
			return new ArrayList<>();
		}

		List<Integer> sortedKeys = new ArrayList<>(pedidoTable.keySet());
		Collections.sort(sortedKeys);

		int startIndex = page * pageSize;
		int endIndex = Math.min(startIndex + pageSize, sortedKeys.size());

		if (startIndex >= sortedKeys.size()) {
			return new ArrayList<>();
		}

		List<PedidoListagemDetalhadaDTO> pagedPedidos = new ArrayList<>();

		for (int i = startIndex; i < endIndex; i++) {
			Integer key = sortedKeys.get(i);
			Pedido pedido = pedidoTable.get(key);

			PedidoListagemDetalhadaDTO dto = PedidoMapper.toListagemDetalhadaDTO(pedido);
			pagedPedidos.add(dto);
		}

		return pagedPedidos;
	}

	public List<PedidoListagemDetalhadaDTO> listAllPaginado(int page, int pageSize) {
		loadPedidosIntoTable();

		return listByPage(page, pageSize);
	}

	public List<Pedido> listAll() {
		return action.findAll();
	}

	public Pedido listById(Integer id) {
		return action.findById(id).orElseThrow(
			() -> new IllegalArgumentException("Não encontrado")
		);
	}

	public List<Pedido> listAllDetailsByStatus(Integer statusPedido) {
		return action.findByStatusPedido_IdEquals(statusPedido);
	}

	public List<VwFiltroPedido> listByStatus(String status, String data, String idPedido) {
		if (status == null || status.isBlank()) status = "%";
		if (data == null || data.isBlank()) {
			data = "%";
		} else {
			data = data + "%";
		}
		if (idPedido == null || idPedido.isBlank()) idPedido = "%";
		return actionFiltro.buscaFiltros(idPedido, data, status);
	}

	public Pedido create(Pedido novoPedido, Long idDoador) {
		Doador doador = doadorService.buscarPorId(idDoador);
		StatusPedido statusPedido = statusPedidoService.findById(novoPedido.getStatusPedido().getId());
		novoPedido.setDoador(doador);
		novoPedido.setStatusPedido(statusPedido);
		return action.save(novoPedido);
	}

	public Pedido statusChange(Integer id, Integer condicao) {
		Pedido pedido = action.findById(id).orElseThrow(
			() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não Encontrado")
		);

		StatusPedido status = statusPedidoService.findById(condicao);
		pedido.setStatusPedido(status);
		this.notifyChange(pedido.getDoador());

		return action.save(pedido);
	}

	public List<Pedido> listAllDetailsByDoador(Integer idDoador) {
		return action.findByDoadorId(idDoador);
	}

	@Override
	public void notifyChange(Doador entity) {
		doadorService.updateListener(entity.getEmail(), "Pedido");
	}


}
