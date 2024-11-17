package com.caixadesapato.api.service;

import com.caixadesapato.api.dto.caixa.CaixaCriacaoDTO;
import com.caixadesapato.api.dto.caixa.CaixaUpdateDTO;
import com.caixadesapato.api.model.*;
import com.caixadesapato.api.repository.CaixaRepository;
import com.caixadesapato.api.utils.enums.Genero;
import com.caixadesapato.api.utils.interfaces.PublisherChange;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CaixaService implements PublisherChange {

	private final CaixaRepository action;
	private final ItemCaixaService itemCaixaService;
	private final PedidoService pedidoService;
	private final EtapaCaixaService etapaService;
	private final DoadorService doadorService;
	private final FaixaEtariaService faixaEtariaService;


	public List<CaixaCriacaoDTO> processarArquivo(MultipartFile file) throws Exception {
		List<CaixaCriacaoDTO> caixasDto = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
			String line;
			while ((line = reader.readLine()) != null) {
				// Verifica se a linha é longa o suficiente
				if (line.length() < 120) {
					throw new IllegalArgumentException("A linha deve ter pelo menos 120 caracteres.");
				}

				CaixaCriacaoDTO caixaDto = new CaixaCriacaoDTO();

				// Leitura posicional
				String generoStr = line.substring(0, 8).trim();
				caixaDto.setGenero(generoStr.equals("FEMININO") ? Genero.F : Genero.M);

				caixaDto.setCarta(line.substring(9, 59).trim());
				caixaDto.setUrl(line.substring(59, 109).trim());
				caixaDto.setQuantidade(Integer.parseInt(line.substring(109, 113).trim()));
				caixaDto.setIdFaixaEtaria(Integer.parseInt(line.substring(113, 117).trim()));

				// Configura itensCaixa manualmente
				int[] itens = {1, 2, 3, 4, 5, 6, 7};
				caixaDto.setItensCaixa(itens);

				caixaDto.setIdPedido(Integer.parseInt(line.substring(117, 121).trim()));

				caixasDto.add(caixaDto);
			}
		}
		return caixasDto;
	}

	public Caixa save(
		Caixa novaCaixa, int[] listIdsProdutos, Integer idPedido, Integer idFaixaEtaria
	) {
		if (Objects.isNull(novaCaixa)) {
			return null;
		}

		Pedido pedido = pedidoService.listById(idPedido);
		FaixaEtaria faixaEtaria = faixaEtariaService.findById(idFaixaEtaria);
		novaCaixa.setPedido(pedido);
		novaCaixa.setFaixaEtaria(faixaEtaria);
		novaCaixa.setDataCriacao(LocalDate.now());

		Caixa caixaRegistrada = action.save(novaCaixa);

		List<ItemCaixa> itens = itemCaixaService.insertItems(caixaRegistrada, listIdsProdutos);
		caixaRegistrada.setItens(itens);

		etapaService.setEtapaCaixa(caixaRegistrada, 1);
		List<EtapaCaixa> etapas = etapaService.etapas(caixaRegistrada.getId());
		caixaRegistrada.setEtapas(etapas);
		return caixaRegistrada;
	}

	public List<Caixa> listAll() {
		return action.findAll();
	}

	public Caixa listByID(Integer id) {
		return action.findById(id).orElseThrow(
			() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado")
		);
	}

	public Caixa findByQrCodeToken(String token) {
		return action.findByQrCodeToken(token).orElseThrow(
			() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado")
		);
	}

	public Caixa update(
		Integer id, CaixaUpdateDTO caixaAtualizada
	) {
		Caixa caixa = action.findById(id).orElseThrow(
			() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado")
		);

		caixa.setCarta(caixaAtualizada.getCarta());
		caixa.setUrl(caixaAtualizada.getUrl());
		caixa.setQuantidade(caixaAtualizada.getQuantidade());
		return action.save(caixa);
	}

	public void statusChange(Integer id, Integer status) {
		Caixa caixa = action.findById(id).orElseThrow(
			() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado")
		);
		etapaService.setEtapaCaixa(caixa, status);
		notifyChange(caixa.getPedido().getDoador());
	}

	public void createDefaultBox(int idPedido) {
		Caixa caixa = new Caixa();
		caixa.setCarta("Quero te contar que você é uma pessoa muito especial e sempre que você sorri, o mundo fica mais bonito.");
		caixa.setUrl("https://digitalhealthskills.com/wp-content/uploads/2022/11/3da39-no-user-image-icon-27.png");
		caixa.setQuantidade(1);
		caixa.setGenero(Genero.F);
		int faixaEtaria = (int) Math.floor((Math.random() * 3));
		save(caixa, new int[]{1, 2, 3, 4, 5, 6, 7}, idPedido, faixaEtaria);
	}

	@Override
	public void notifyChange(Doador entity) {
		doadorService.updateListener(entity.getEmail(), "Caixa");
	}
}
