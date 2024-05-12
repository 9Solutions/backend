package com.example.apirestv2.service.caixa;

import com.example.apirestv2.domain.caixa.Caixa;
import com.example.apirestv2.domain.caixa.repository.CaixaRepository;
import com.example.apirestv2.domain.itemCaixa.ItemCaixa;
import com.example.apirestv2.domain.pedido.Pedido;
import com.example.apirestv2.service.caixa.dto.CaixaCriacaoDTO;
import com.example.apirestv2.service.caixa.dto.CaixaListagemDTO;
import com.example.apirestv2.service.caixa.dto.CaixaMapper;
import com.example.apirestv2.service.caixa.dto.CaixaUpdateDTO;
import com.example.apirestv2.service.itemCaixa.ItemCaixaService;
import com.example.apirestv2.service.itemCaixa.dto.ItemCaixaMapper;
import com.example.apirestv2.service.itemCaixa.dto.ItemsCaixaDTO;
import com.example.apirestv2.service.pedido.PedidoService;
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
public class CaixaService {

    @Autowired
    private CaixaRepository action;

    @Autowired
    private ItemCaixaService itemCaixaService;

    @Autowired
    private PedidoService pedidoService;


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

}
