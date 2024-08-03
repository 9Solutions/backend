package com.caixadesapato.api.service;

import com.caixadesapato.api.dto.caixa.CaixaUpdateDTO;
import com.caixadesapato.api.model.*;
import com.caixadesapato.api.repository.CaixaRepository;
import com.caixadesapato.api.utils.interfaces.PublisherChange;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public Caixa save(
            Caixa novaCaixa, int[] listIdsProdutos, Integer idPedido, Integer idFaixaEtaria
    ) {
        if(!Objects.isNull(novaCaixa)){
            Pedido pedido = pedidoService.listById(idPedido);
            novaCaixa.setPedido(pedido);

            FaixaEtaria faixaEtaria = faixaEtariaService.findById(idFaixaEtaria);
            novaCaixa.setFaixaEtaria(faixaEtaria);

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
        notifyChange(caixa.getPedido().getDoador());
    }

    @Override
    public void notifyChange(Doador entity) {
        doadorService.updateListener(entity.getEmail(), "Caixa");
    }

}
