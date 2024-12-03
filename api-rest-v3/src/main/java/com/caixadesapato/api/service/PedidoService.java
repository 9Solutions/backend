package com.caixadesapato.api.service;

import com.caixadesapato.api.dto.doador.DoadorCriacaoDTO;
import com.caixadesapato.api.dto.pedido.ImportPedidoDTO;
import com.caixadesapato.api.dto.pedido.PedidoCriacaoDTO;
import com.caixadesapato.api.dto.pedido.PedidoListagemDetalhadaDTO;
import com.caixadesapato.api.dto.pedido.PedidoMapper;
import com.caixadesapato.api.model.Caixa;
import com.caixadesapato.api.model.Doador;
import com.caixadesapato.api.model.Pedido;
import com.caixadesapato.api.model.StatusPedido;
import com.caixadesapato.api.model.view.VwFiltroPedido;
import com.caixadesapato.api.repository.PedidoRepository;
import com.caixadesapato.api.repository.view.VwFiltrosPedidosRepository;
import com.caixadesapato.api.utils.enums.Genero;
import com.caixadesapato.api.utils.interfaces.PublisherChange;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

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

	public List<ImportPedidoDTO> importarDeTxt(MultipartFile file) throws IOException {
		try {
			Reader streamFile = new InputStreamReader(file.getInputStream());
			BufferedReader reader = new BufferedReader(streamFile);
			String linha;

			List<ImportPedidoDTO> informacoesImport = new ArrayList<>();

			while ((linha = reader.readLine()) != null) {
				String tipoRegistro = linha.substring(0, 2);

				switch (tipoRegistro) {
					case "00":
						String identificadorArquivo = linha.substring(2, 12).trim();
						String dataHoraGeracao = linha.substring(12, 31).trim();
						String versaoLayout = linha.substring(31, 33).trim();
						String nomeAdmin = linha.substring(33, 63).trim();
						Integer quantidadeRegistros = Integer.parseInt(linha.substring(64, 69).trim());
						break;

					case "01":
						String status = linha.substring(2, 12).trim();
						String dataPedido = linha.substring(12, 24).trim();
						Double valorTotal = Double.parseDouble(linha.substring(24, 32).trim());
						String cpfDoador = linha.substring(32, 43).trim();
						String emailDoador = linha.substring(43, 104).trim();
						String nomeDoador = linha.substring(104, 134).trim();

						Doador doador = doadorService.buscarPorCpf(cpfDoador);
						if(Objects.isNull(doador)) {
							DoadorCriacaoDTO doadorDTO =
									new DoadorCriacaoDTO(
											nomeDoador,
											cpfDoador,
											emailDoador,
											"Não Informado",
											cpfDoador,
											"User by Import"
									);
							doador = doadorService.cadastrar(doadorDTO);
						}

						StatusPedido searchedStatus = statusPedidoService.findByStatus(status.toLowerCase());

						Pedido pedido = new Pedido();
						pedido.setValorTotal(valorTotal);
						pedido.setDoador(doador);
						pedido.setStatusPedido(searchedStatus);
						Pedido pedidoSalvo = action.save(pedido);
						informacoesImport.add(new ImportPedidoDTO(pedidoSalvo.getId(), (int) Math.floor(valorTotal / 88)));
						break;

					default:
						throw new IllegalArgumentException("Tipo de registro desconhecido: " + tipoRegistro);
				}
			}
			return informacoesImport;
		} catch (IOException e) {
			throw new IOException("Erro ao processar arquivo");
		}
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

	public byte[] exportarParaCsv() {
		// exportar para csv retornando um arquivo
		StringBuilder txtBuilder = new StringBuilder();
		try {
			for (Pedido pedido : listAll()) {
				txtBuilder.append(String.format("%d,%s,%s,%.2f\n",
						pedido.getId(), pedido.getDoador().getNomeCompleto(),
						pedido.getStatusPedido().getStatus(), pedido.getValorTotal())
				);
			}
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao exportar para CSV");
		}
		return txtBuilder.toString().getBytes(StandardCharsets.UTF_8);
	}
}
