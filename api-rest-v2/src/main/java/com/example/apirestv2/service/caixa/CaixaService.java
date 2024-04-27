package com.example.apirestv2.service.caixa;

import com.example.apirestv2.domain.caixa.Caixa;
import com.example.apirestv2.domain.caixa.repository.CaixaRepository;
import com.example.apirestv2.domain.itemCaixa.ItemCaixa;
import com.example.apirestv2.service.caixa.dto.CaixaCriacaoDTO;
import com.example.apirestv2.service.caixa.dto.CaixaListagemDTO;
import com.example.apirestv2.service.caixa.dto.CaixaMapper;
import com.example.apirestv2.service.itemCaixa.ItemCaixaService;
import com.example.apirestv2.service.itemCaixa.dto.ItemCaixaMapper;
import com.example.apirestv2.service.itemCaixa.dto.ItemsCaixaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

import java.util.Objects;
import java.util.Optional;

@Service
public class CaixaService {

    @Autowired
    private CaixaRepository action;

    @Autowired
    private ItemCaixaService itemCaixaService;

    public ResponseEntity<CaixaListagemDTO> create(
            CaixaCriacaoDTO novaCaixa
    ){
        if(!Objects.isNull(novaCaixa)){
            Caixa caixa = action.save(CaixaMapper.toEntity(novaCaixa));

            boolean madeInsertion = itemCaixaService.insertItems(
                    caixa.getId(), novaCaixa.getItensCaixa()
            );

            if(madeInsertion){
                return ResponseEntity.status(201).build();
            }
        }
        return ResponseEntity.status(400).build();
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


    public ResponseEntity<List<ItemCaixa>> listByIdItemsCaixa(Integer id){

        Optional<Caixa> caixa = action.findById(id);
        if(caixa.isPresent()){
            List<ItemCaixa> listaDeItemsDaCaixa = itemCaixaService.listByIdItemsCaixa(id);
            if(!listaDeItemsDaCaixa.isEmpty()){
                return ResponseEntity.status(200).body(listaDeItemsDaCaixa);
            }
        }
        return ResponseEntity.status(404).build();
    }

}
