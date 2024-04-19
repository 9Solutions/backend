package com.example.apirestv2.service.caixa;

import com.example.apirestv2.domain.caixa.Caixa;
import com.example.apirestv2.domain.caixa.repository.CaixaRepository;
import com.example.apirestv2.service.caixa.dto.CaixaCriacaoDTO;
import com.example.apirestv2.service.caixa.dto.CaixaListagemDTO;
import com.example.apirestv2.service.caixa.dto.CaixaMapper;
import com.example.apirestv2.service.itemCaixa.ItemCaixaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.Objects;

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

            // criar uma método no ItemCaixaService que recebe o id da caixa retrnado pelo save
            // junto do id, passar o array que ainda existe na novaCaixa
            itemCaixaService.insertItems(
                    caixa.getId(), novaCaixa.getItensCaixa()
            );

        }
        return ResponseEntity.status(400).build();


    }

}
