package com.caixadesapato.api.service;

import com.caixadesapato.api.model.StatusCaixa;
import com.caixadesapato.api.repository.StatusCaixaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusCaixaService {

    private final StatusCaixaRepository action;

    public List<StatusCaixa> listAll(){
        return action.findAll();
    }

}
