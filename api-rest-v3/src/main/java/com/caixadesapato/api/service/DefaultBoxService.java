package com.caixadesapato.api.service;

import com.caixadesapato.api.dto.pedido.ImportPedidoDTO;
import com.caixadesapato.api.model.Caixa;
import com.caixadesapato.api.utils.enums.Genero;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;

@RequiredArgsConstructor
@Service
public class DefaultBoxService {

    public void createDefaultBox(CaixaService service, List<ImportPedidoDTO> importPedidos) {
        for(ImportPedidoDTO importPedido : importPedidos) {
            for (int i = 0; i < importPedido.getQuantidadeCaixas(); i++) {
                Caixa caixa = new Caixa();
                caixa.setCarta("Quero te contar que você é uma pessoa muito especial e sempre que você sorri, o mundo fica mais bonito.");
                caixa.setUrl("https://digitalhealthskills.com/wp-content/uploads/2022/11/3da39-no-user-image-icon-27.png");
                caixa.setQuantidade(1);
                caixa.setGenero(Genero.F);
                int faixaEtaria = (int) Math.floor((Math.random() * 3)) + 1;
                service.save(caixa, new int[]{1, 2, 3, 4, 5, 6, 7}, importPedido.getId(), faixaEtaria);
            }
        }
    }
}
