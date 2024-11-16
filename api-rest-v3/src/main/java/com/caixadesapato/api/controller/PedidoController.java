package com.caixadesapato.api.controller;

import com.caixadesapato.api.dto.caixa.CaixaCriacaoDTO;
import com.caixadesapato.api.dto.caixa.CaixaMapper;
import com.caixadesapato.api.dto.pedido.*;
import com.caixadesapato.api.model.Caixa;
import com.caixadesapato.api.model.Pedido;
import com.caixadesapato.api.model.view.VwFiltroPedido;
import com.caixadesapato.api.service.CaixaService;
import com.caixadesapato.api.service.DefaultBoxService;
import com.caixadesapato.api.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Import;
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
	private final CaixaService caixaService;
	private final DefaultBoxService defaultBoxService;

	@PostMapping("/import")
	public ResponseEntity<?> importarPedidosDeTxt(@RequestParam("file") MultipartFile file) {
		if (file.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O arquivo está vazio.");
		}
		try {
			List<ImportPedidoDTO> pedidosImport = service.importarDeTxt(file);
			defaultBoxService.createDefaultBox(caixaService, pedidosImport);
			return ResponseEntity.noContent().build();

		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					"Erro ao processar o arquivo: " + e.getMessage()
			);
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

//	@PostMapping("/import")
//	public ResponseEntity<String> importCaixas(@RequestParam("file") MultipartFile file) {
//		try {
//			List<CaixaCriacaoDTO> caixas = service.processarArquivo(file);
//			for (CaixaCriacaoDTO caixa : caixas) {
//				Caixa caixaNova = CaixaMapper.toEntity(caixa);
//				System.out.println(caixaNova);
//				service.save(caixaNova, caixa.getItensCaixa(), caixa.getIdPedido(), caixa.getIdFaixaEtaria());
//			}
//
//			return ResponseEntity.ok("Importação concluída com sucesso!");
//		} catch (Exception e) {
//			return ResponseEntity.status(500).body("Erro na importação: " + e.getMessage());
//		}
//	}

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
