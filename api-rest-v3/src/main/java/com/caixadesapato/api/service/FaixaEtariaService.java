package com.caixadesapato.api.service;

import com.caixadesapato.api.dto.faixaEtaria.FaixaEtariaUpdateDTO;
import com.caixadesapato.api.model.FaixaEtaria;
import com.caixadesapato.api.repository.FaixaEtariaRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class FaixaEtariaService {

    private final FaixaEtariaRepository action;

    public List<FaixaEtaria> listAll() {
        return action.findAll();
    }

    public FaixaEtaria findById(Integer id) {
        return action.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado")
        );
    }

    public FaixaEtaria create(FaixaEtaria faixaEtariaNova) {
        return action.save(faixaEtariaNova);
    }

    public FaixaEtaria update(Integer id, FaixaEtariaUpdateDTO faixaEtariaUpdateDTO){
        FaixaEtaria faixaEtaria = action.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado")
        );

        faixaEtaria.setFaixaNome(faixaEtariaUpdateDTO.getFaixaNome());
        return action.save(faixaEtaria);
    }

}
