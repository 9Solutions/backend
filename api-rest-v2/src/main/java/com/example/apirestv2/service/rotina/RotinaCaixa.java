package com.example.apirestv2.service.rotina;

import com.example.apirestv2.domain.caixa.Caixa;
import com.example.apirestv2.domain.caixa.repository.CaixaRepository;
import com.example.apirestv2.domain.itemCaixa.ItemCaixa;
import com.example.apirestv2.domain.pedido.Pedido;
import com.example.apirestv2.service.caixa.CaixaService;
import com.example.apirestv2.service.caixa.EtapaCaixaService;
import com.example.apirestv2.service.doador.DoadorService;
import com.example.apirestv2.service.itemCaixa.ItemCaixaService;
import com.example.apirestv2.service.pedido.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RotinaCaixa {

    private FilaObj<NovaCaixa> filaDeCaixas = new FilaObj<>(20);
    private final CaixaRepository action;
    private final ItemCaixaService itemCaixaService;
    private final PedidoService pedidoService;
    private final EtapaCaixaService etapaService;
    private final DoadorService doadorService;

    public void colocarNaFila(Caixa novaCaixa, int[] listIdsProdutos, Integer idPedido) {
        NovaCaixa caixa = new NovaCaixa();
        caixa.setCaixas(novaCaixa);
        caixa.setItens(listIdsProdutos);
        caixa.setIdsPedidos(idPedido);
        filaDeCaixas.insert(caixa);
    }

    @Scheduled(cron = "1 * * * * *")
    public void saveInDatabase() {
        NovaCaixa caixaDaVez = filaDeCaixas.poll();
        if (Objects.isNull(caixaDaVez)) return;
        this.save(caixaDaVez.getCaixas(), caixaDaVez.getItens(), caixaDaVez.getIdsPedidos());
    }

    public void save(
            Caixa novaCaixa, int[] listIdsProdutos, Integer idPedido
    ) {
        if(!Objects.isNull(novaCaixa)){
            Pedido pedido = pedidoService.listById(idPedido);
            novaCaixa.setPedido(pedido);
            Caixa caixaSalva = action.save(novaCaixa);

            List<ItemCaixa> madeInsertion = itemCaixaService.insertItems(
                    caixaSalva, listIdsProdutos
            );

            etapaService.mudarEtapaCaixa(caixaSalva, 1);

            if(madeInsertion.isEmpty()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Itens não cadastrados");
            } else {
                caixaSalva.setItens(madeInsertion);
            }
        }
    }

}
