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
    ){
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


    public ResponseEntity<List<CaixaListagemDTO>> listAll(){
        List<Caixa> listaCaixas = action.findAll();

        if(!listaCaixas.isEmpty()){
            List<CaixaListagemDTO> caixasDTO = CaixaMapper.toDTO(listaCaixas);
            return ResponseEntity.status(200).body(caixasDTO);
        }
        return ResponseEntity.status(204).build();

    }


    public ResponseEntity<CaixaListagemDTO> listByID(Integer id){
        Optional<Caixa> caixa = action.findById(id);
        if(caixa.isPresent()){
            CaixaListagemDTO dto = CaixaMapper.toDTO(caixa.get());
            return ResponseEntity.status(200).body(dto);
        }
        return ResponseEntity.status(404).build();
    }


    public ResponseEntity<Caixa> listByIdItemsCaixa(Integer id){

        Optional<Caixa> caixa = action.findById(id);
        if(caixa.isPresent()){
            return ResponseEntity.status(200).body(caixa.get());
        }
        return ResponseEntity.status(404).build();
    }


    public ResponseEntity<CaixaListagemDTO> update(Integer id, CaixaUpdateDTO caixaAtualizada){

        Optional<Caixa> caixa = action.findById(id);
        if(caixa.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        caixa.get().setCarta(caixaAtualizada.getCarta());
        caixa.get().setUrl(caixaAtualizada.getUrl());
        caixa.get().setQuantidade(caixaAtualizada.getQuantidade());

        Caixa caixaAtualizadaBanco = action.save(caixa.get());
        CaixaListagemDTO caixaDTO = CaixaMapper.toDTO(caixaAtualizadaBanco);
        return ResponseEntity.ok(caixaDTO);

    }

}
