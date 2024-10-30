package com.caixadesapato.api.controller;

import com.caixadesapato.api.dto.pedido.*;
import com.caixadesapato.api.model.Pedido;
import com.caixadesapato.api.model.view.VwFiltroPedido;
import com.caixadesapato.api.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

	private final PedidoService service;

	@PostMapping("/importar-txt")
	public ResponseEntity<?> importarPedidosDeTxt(@RequestParam("file") MultipartFile file) {


		if (file.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O arquivo está vazio.");
		}

		try {
			String fileName = file.getOriginalFilename();

			String filePath = System.getProperty("java.io.tmpdir") + "/" + fileName;
			file.transferTo(new java.io.File(filePath));
			leArquivoTxt(filePath);

			return ResponseEntity.ok("Arquivo processado com sucesso.");
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar o arquivo: " + e.getMessage());
		}
	}

	public static void leArquivoTxt(String nomeArq) {

		try (BufferedReader entrada = new BufferedReader(new FileReader(nomeArq))) {
			List<String> listaRegistros = new ArrayList<>();
			String linha;

			while ((linha = entrada.readLine()) != null) {
				String tipoRegistro = linha.substring(0, 2);

				if (tipoRegistro.equals("00")) {
					String identificador = linha.substring(2, 12).trim();
					String dataGeracao = linha.substring(12, 31).trim();
					String versao = linha.substring(31, 33).trim();
					String nomeAdmin = linha.substring(33, 63).trim();
					String quantidadeRegistros = linha.substring(63, 69).trim();

					System.out.println("Registro Header:");
					System.out.println("Identificador: " + identificador);
					System.out.println("Data/Hora: " + dataGeracao);
					System.out.println("Versão: " + versao);
					System.out.println("Nome do Admin: " + nomeAdmin);
					System.out.println("Quantidade de Registros: " + quantidadeRegistros);
				} else if (tipoRegistro.equals("01")) {
					int idPedido = Integer.parseInt(linha.substring(2, 8).trim());
					String nomeDoador = linha.substring(8, 38).trim();
					String cpfDoador = linha.substring(38, 49).trim();
					String statusPedido = linha.substring(49, 59).trim();
					String dataPedido = linha.substring(59, 69).trim();
					double valorPedido = Double.parseDouble(linha.substring(69, 77).trim().replace(",", "."));

					System.out.println("Detalhes do Pedido:");
					System.out.println("ID do Pedido: " + idPedido);
					System.out.println("Nome do Doador: " + nomeDoador);
					System.out.println("CPF do Doador: " + cpfDoador);
					System.out.println("Status do Pedido: " + statusPedido);
					System.out.println("Data do Pedido: " + dataPedido);
					System.out.println("Valor do Pedido: " + valorPedido);
				} else if (tipoRegistro.equals("02")) {
					String nomeDoador = linha.substring(2, 32).trim();
					String cpfDoador = linha.substring(32, 43).trim();
					String emailDoador = linha.substring(43, 69).trim();

					System.out.println("Detalhes do Doador:");
					System.out.println("Nome do Doador: " + nomeDoador);
					System.out.println("CPF do Doador: " + cpfDoador);
					System.out.println("Email do Doador: " + emailDoador);
				} else {
					System.out.println("Tipo de registro desconhecido: " + tipoRegistro);
				}
			}

		} catch (IOException e) {
			System.out.println("Erro ao ler o arquivo: " + e.getMessage());
		}
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

	@GetMapping("/exportar")
	public ResponseEntity<byte[]> exportarPedidos(@RequestParam("tipo") String tipo) {
		List<PedidoListagemDetalhadaDTO> pedidos = PedidoMapper.toListagemDetalhadaDTO(service.listAll());

		byte[] arquivoExportado = service.exportarPedidos(pedidos, tipo);

		String filename = "pedidos." + tipo.toLowerCase();
		String contentType = obterContentType(tipo);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentDispositionFormData("attachment", filename);
		headers.setContentType(MediaType.parseMediaType(contentType));

		return ResponseEntity.ok()
			.headers(headers)
			.body(arquivoExportado);
	}

	@GetMapping("/exportar-txt")
	public ResponseEntity<byte[]> exportarPedidosParaTxt(@RequestParam String nomeAdmin) {
		List<PedidoListagemDetalhadaDTO> pedidos = PedidoMapper.toListagemDetalhadaDTO(service.listAll());

		byte[] conteudoTxt = service.exportarParaTxt(pedidos, nomeAdmin);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_PLAIN);
		headers.setContentDispositionFormData("attachment", "pedidos.txt");

		return new ResponseEntity<>(conteudoTxt, headers, HttpStatus.OK);
	}


	@Operation(summary = "Listar", description = "Método que retorna todos os dados de forma simplificada", tags = "Pedidos")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Lista de pedidos"),
		@ApiResponse(responseCode = "204", description = "Lista de pedidos vazia"),
		@ApiResponse(responseCode = "500", description = "Internal Server Error"),
	})
	@GetMapping
	public ResponseEntity<List<PedidoListagemSimplesDTO>> listAll() {

		List<Pedido> pedidos = service.listAll();
		if (pedidos.isEmpty()) return ResponseEntity.noContent().build();

		List<PedidoListagemSimplesDTO> pedidosDTO = PedidoMapper.toListagemSimplesdDTO(pedidos);
		return ResponseEntity.ok(pedidosDTO);

	}


	@Operation(summary = "Listar por ID", description = "Método que retorna os dados de um pedido", tags = "Pedidos")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Listar dados do pedido"),
		@ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
		@ApiResponse(responseCode = "500", description = "Internal Server Error"),
	})
	@GetMapping("/{id}")
	public ResponseEntity<PedidoListagemSimplesDTO> listById(@PathVariable Integer id) {
		Pedido pedido = service.listById(id);
		PedidoListagemSimplesDTO pedidoDTO = PedidoMapper.toListagemSimplesdDTO(pedido);
		return ResponseEntity.ok(pedidoDTO);
	}

	@Operation(summary = "Listar os dados por filtros", description = "Método que retorna os dados dos pedidos aplicando os filtros", tags = "Pedidos")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Lista de pedidos"),
		@ApiResponse(responseCode = "204", description = "Lista vazia"),
		@ApiResponse(responseCode = "500", description = "Internal Server Error"),
	})
	@GetMapping("/filter")
	public ResponseEntity<List<VwFiltroPedido>> listByStatus(
		@RequestParam String status,
		@RequestParam String data,
		@RequestParam String idPedido
	) {
		status = status.replace("+", " ");
		List<VwFiltroPedido> pedidos = service.listByStatus(status, data, idPedido);

		if (pedidos.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(pedidos);
	}


	@Operation(summary = "Listar detalhes do pedido por parametro", description = "Método que retorna os dados de um pedido de forma detalhada", tags = "Pedidos")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Lista dados do pedido"),
		@ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
		@ApiResponse(responseCode = "500", description = "Internal Server Error"),
	})
	@GetMapping("/all-details")
	public ResponseEntity<List<PedidoListagemDetalhadaDTO>> listAllDetailsByParams(
		@RequestParam(required = false) Integer statusPedido
	) {
		List<Pedido> pedido;
		if (statusPedido == null) {
			pedido = service.listAll();
		} else {
			pedido = service.listAllDetailsByStatus(statusPedido);
		}
		List<PedidoListagemDetalhadaDTO> pedidosDTO = PedidoMapper.toListagemDetalhadaDTO(pedido);
		return ResponseEntity.ok(pedidosDTO);
	}

	@Operation(summary = "Listar detalhes do pedido por parametro", description = "Método que retorna os dados de um pedido de forma detalhada", tags = "Pedidos")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Lista dados do pedido"),
		@ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
		@ApiResponse(responseCode = "500", description = "Internal Server Error"),
	})
	@GetMapping("/all-details/buscar-doador/{idDoador}")
	public ResponseEntity<List<PedidoListagemDetalhadaDTO>> listAllDetailsByDoador(
		@PathVariable Integer idDoador
	) {
		List<Pedido> pedidos = service.listAllDetailsByDoador(idDoador);
		if (pedidos.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		List<PedidoListagemDetalhadaDTO> pedidosDTO = PedidoMapper.toListagemDetalhadaDTO(pedidos);
		return ResponseEntity.ok(pedidosDTO);
	}


	@Operation(summary = "Listar detalhes do pedido por parametro", description = "Método que retorna os dados de um pedido de forma detalhada", tags = "Pedidos")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Lista dados do pedido"),
		@ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
		@ApiResponse(responseCode = "500", description = "Internal Server Error"),
	})
	@GetMapping("/all-details/{id}")
	public ResponseEntity<PedidoListagemDetalhadaDTO> listAllDetailsById(
		@PathVariable Integer id
	) {
		Pedido pedido = service.listById(id);
		PedidoListagemDetalhadaDTO pedidoDTO = PedidoMapper.toListagemDetalhadaDTO(pedido);
		return ResponseEntity.ok(pedidoDTO);
	}


	@Operation(summary = "Criar um novo pedido", description = "Método responsável por criar um novo pedido", tags = "Pedidos")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "Pedido cadastrado com sucesso"),
		@ApiResponse(responseCode = "400", description = "Atributo inválido"),
		@ApiResponse(responseCode = "404", description = "Doador não encontrado"),
		@ApiResponse(responseCode = "500", description = "Internal Server Error"),
	})
	@PostMapping
	public ResponseEntity<PedidoListagemSimplesDTO> create(
		@RequestBody @Valid PedidoCriacaoDTO novoPedido
	) {
		Pedido pedido = PedidoMapper.toEntity(novoPedido);
		Pedido pedidoCriado = service.create(pedido, novoPedido.getIdDoador());
		PedidoListagemSimplesDTO pedidoDTO = PedidoMapper.toListagemSimplesdDTO(pedidoCriado);

		service.adicionarPedido(pedidoDTO.getId(), pedidoCriado);
		return ResponseEntity.ok(pedidoDTO);
	}


	@Operation(summary = "Alterar STATUS do pedido", description = "Método responsável por alterar o STATUS de um pedido", tags = "Pedidos")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "Status do pedido atualizado com sucesso"),
		@ApiResponse(responseCode = "400", description = "Atributo inválido", useReturnTypeSchema = false),
		@ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
		@ApiResponse(responseCode = "500", description = "Internal Server Error"),
	})
	@PatchMapping
	public ResponseEntity<PedidoListagemSimplesDTO> statusChange(
		@RequestParam Integer id,
		@RequestParam Integer condicao
	) {
		Pedido pedidoAtualizado = service.statusChange(id, condicao);
		PedidoListagemSimplesDTO pedidoDTO = PedidoMapper.toListagemSimplesdDTO(pedidoAtualizado);
		return ResponseEntity.ok(pedidoDTO);
	}

	@GetMapping("/paginado")
	public ResponseEntity<List<PedidoListagemDetalhadaDTO>> listAllByPage(
		@RequestParam(defaultValue = "0") Integer page
	) {
		int pageSize = 15;

		List<PedidoListagemDetalhadaDTO> pedidos = service.listAllPaginado(page, pageSize);

		if (pedidos.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(pedidos);
	}

}
