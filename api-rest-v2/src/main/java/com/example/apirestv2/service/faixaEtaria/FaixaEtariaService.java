package com.example.apirestv2.service.faixaEtaria;

import com.example.apirestv2.domain.faixaEtaria.FaixaEtaria;
import com.example.apirestv2.domain.faixaEtaria.repository.FaixaEtariaRepository;
import com.example.apirestv2.service.faixaEtaria.dto.FaixaEtariaCriacaoDTO;
import com.example.apirestv2.service.faixaEtaria.dto.FaixaEtariaListagemDTO;
import com.example.apirestv2.service.faixaEtaria.dto.FaixaEtariaMapper;
import com.example.apirestv2.service.faixaEtaria.dto.FaixaEtariaUpdateDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FaixaEtariaService {
    @Autowired
    private FaixaEtariaRepository action;

    public List<FaixaEtaria> listAll(){ return action.findAll(); }

    public FaixaEtaria findById(Integer id){
           return action.findById(id).orElseThrow(
                   () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado")
           );
    }

    public FaixaEtaria create(FaixaEtaria faixaEtariaNova){ return action.save(faixaEtariaNova); }


    public FaixaEtaria update(Integer id, FaixaEtariaUpdateDTO faixaEtariaUpdateDTO){
        FaixaEtaria faixaEtaria = action.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado")
        );

        faixaEtaria.setFaixaNome(faixaEtariaUpdateDTO.getFaixaNome());
        return action.save(faixaEtaria);
    }

    public ResponseEntity<Void> delete(Integer id){
        FaixaEtaria faixaEtaria = action.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado")
        );

        action.delete(faixaEtaria);
        return ResponseEntity.noContent().build();
    }

}
