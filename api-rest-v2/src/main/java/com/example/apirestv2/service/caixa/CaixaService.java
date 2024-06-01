package com.example.apirestv2.service.caixa;

import com.example.apirestv2.domain.caixa.Caixa;
import com.example.apirestv2.domain.caixa.EtapaCaixa;
import com.example.apirestv2.domain.caixa.repository.CaixaRepository;
import com.example.apirestv2.domain.caixa.repository.EtapaCaixaRepository;
import com.example.apirestv2.domain.itemCaixa.ItemCaixa;
import com.example.apirestv2.domain.pedido.Pedido;
import com.example.apirestv2.service.caixa.dto.CaixaCriacaoDTO;
import com.example.apirestv2.service.caixa.dto.CaixaListagemDTO;
import com.example.apirestv2.service.caixa.dto.CaixaMapper;
import com.example.apirestv2.service.caixa.dto.CaixaUpdateDTO;
import com.example.apirestv2.service.interfaces.ChangeListener;
import com.example.apirestv2.service.itemCaixa.ItemCaixaService;
import com.example.apirestv2.service.itemCaixa.dto.ItemCaixaMapper;
import com.example.apirestv2.service.itemCaixa.dto.ItemsCaixaDTO;
import com.example.apirestv2.service.pedido.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CaixaService implements ChangeListener {

    private final CaixaRepository action;
    private final ItemCaixaService itemCaixaService;
    private final PedidoService pedidoService;
    private final EtapaCaixaService etapaService;

    public Caixa create(
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
                return caixaSalva;
            }
        }
        return null;
    }


    public List<Caixa> listAll(){
        return action.findAll();
    }


    public Caixa listByID(Integer id){
        return action.findById(id).orElseThrow(
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
        etapaService.mudarEtapaCaixa(caixa, status);
        updateListener(caixa.getPedido().getDoador().getEmail(), "Caixa");
    }

    @Override
    public void updateListener(String email, String eventType) {
        System.out.println(String.format("Enviado para: %s | Atualização: O(a) %s teve o status alterado", email, eventType));
    }
}
