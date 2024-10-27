package com.caixadesapato.api.service;

import com.caixadesapato.api.dto.pedido.PedidoListagemDetalhadaDTO;
import com.caixadesapato.api.dto.pedido.PedidoMapper;
import com.caixadesapato.api.model.Doador;
import com.caixadesapato.api.model.Pedido;
import com.caixadesapato.api.model.StatusPedido;
import com.caixadesapato.api.model.view.VwFiltroPedido;
import com.caixadesapato.api.repository.PedidoRepository;
import com.caixadesapato.api.repository.view.VwFiltrosPedidosRepository;
import com.caixadesapato.api.utils.interfaces.PublisherChange;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.parquet.example.data.Group;
import org.apache.parquet.example.data.simple.SimpleGroupFactory;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.example.GroupWriteSupport;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;
import org.apache.parquet.schema.MessageType;
import org.apache.parquet.schema.MessageTypeParser;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.OutputStreamWriter;
import java.util.List;




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

	private String obterContentType(String tipo) {
		switch (tipo.toLowerCase()) {
			case "json":
				return "application/json";
			case "csv":
				return "text/csv";
			case "xml":
				return "application/xml";
			case "parquet":
				return "application/octet-stream";
			case "txt":
				return "text/plain";
			default:
				throw new IllegalArgumentException("Tipo de arquivo não suportado: " + tipo);
		}
	}

	public byte[] exportarPedidos(List<PedidoListagemDetalhadaDTO> pedidos, String tipo) {
		switch (tipo.toLowerCase()) {
			case "json":
				return exportarParaJson(pedidos);
			case "csv":
				return exportarParaCsv(pedidos);
			case "xml":
				return exportarParaXml(pedidos);
			default:
				throw new IllegalArgumentException("Tipo de arquivo não suportado: " + tipo);
		}
	}

	private byte[] exportarParaJson(List<PedidoListagemDetalhadaDTO> pedidos) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsBytes(pedidos);
		} catch (IOException e) {
			throw new RuntimeException("Erro ao exportar para JSON", e);
		}
	}

	private byte[] exportarParaCsv(List<PedidoListagemDetalhadaDTO> pedidos) {
		try (ByteArrayOutputStream out = new ByteArrayOutputStream();
		     CSVPrinter printer = new CSVPrinter(new OutputStreamWriter(out, StandardCharsets.UTF_8),
			     CSVFormat.DEFAULT.withHeader("ID", "Status", "Doador", "Valor Total"))) {
			for (PedidoListagemDetalhadaDTO pedido : pedidos) {
				printer.printRecord(pedido.getId(), pedido.getStatus(), pedido.getDoador().getNome(), pedido.getValorTotal());
			}
			printer.flush();
			return out.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException("Erro ao exportar para CSV", e);
		}
	}

	private byte[] exportarParaXml(List<PedidoListagemDetalhadaDTO> pedidos) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();

			Element rootElement = doc.createElement("Pedidos");
			doc.appendChild(rootElement);

			for (PedidoListagemDetalhadaDTO pedido : pedidos) {
				Element pedidoElement = doc.createElement("Pedido");
				rootElement.appendChild(pedidoElement);

				Element idElement = doc.createElement("ID");
				idElement.appendChild(doc.createTextNode(String.valueOf(pedido.getId())));
				pedidoElement.appendChild(idElement);

				Element statusElement = doc.createElement("Status");
				statusElement.appendChild(doc.createTextNode(pedido.getStatus()));
				pedidoElement.appendChild(statusElement);

				Element doadorElement = doc.createElement("Doador");
				doadorElement.appendChild(doc.createTextNode(pedido.getDoador().getNome()));
				pedidoElement.appendChild(doadorElement);

				Element valorTotalElement = doc.createElement("ValorTotal");
				valorTotalElement.appendChild(doc.createTextNode(String.valueOf(pedido.getValorTotal())));
				pedidoElement.appendChild(valorTotalElement);
			}

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			javax.xml.transform.Transformer transformer = javax.xml.transform.TransformerFactory.newInstance().newTransformer();
			javax.xml.transform.dom.DOMSource source = new javax.xml.transform.dom.DOMSource(doc);
			javax.xml.transform.stream.StreamResult result = new javax.xml.transform.stream.StreamResult(outputStream);
			transformer.transform(source, result);

			return outputStream.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException("Erro ao exportar para XML", e);
		}
	}


	public byte[] exportarParaTxt(List<PedidoListagemDetalhadaDTO> pedidos, String nomeAdmin) {
		StringBuilder txtBuilder = new StringBuilder();

		// Registro Header
		String header = String.format(
			"%-2s%-10s%-19s%-2s%-30s%06d",
			"00",
			"PEDIDOS",
			LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")),
			"01",
			nomeAdmin,
			pedidos.size()
		);
		txtBuilder.append(header).append("\n");

		// Registros Detalhes do Pedido (Tipo 01)
		for (PedidoListagemDetalhadaDTO pedido : pedidos) {
			String detalhePedido = String.format(
				"%-2s%06d%-30s%-11s%-10s%-10s%08.2f",
				"01",
				pedido.getId(),
				pedido.getDoador().getNome(),
				formatarCpf(pedido.getDoador().getId()),
				pedido.getStatus(),
				LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
				pedido.getValorTotal()
			);
			txtBuilder.append(detalhePedido).append("\n");

			// Registros Detalhes do Doador (Tipo 02)
			String detalheDoador = String.format(
				"%-2s%-30s%-11s%-26s",
				"02",
				pedido.getDoador().getNome(),
				formatarCpf(pedido.getDoador().getId()),
				pedido.getDoador().getEmail()
			);
			txtBuilder.append(detalheDoador).append("\n");
		}

		return txtBuilder.toString().getBytes(StandardCharsets.UTF_8);
	}

	private String formatarCpf(Long cpf) {
		return String.format("%011d", cpf);
	}

}
