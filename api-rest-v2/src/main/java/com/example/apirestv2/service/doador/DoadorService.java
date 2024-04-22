package com.example.apirestv2.service.doador;

import com.example.apirestv2.domain.doador.Doador;
import com.example.apirestv2.domain.doador.repository.DoadorRepository;
import com.example.apirestv2.service.doador.dto.DoadorCriacaoDTO;
import com.example.apirestv2.service.doador.dto.DoadorLoginDTO;
import com.example.apirestv2.service.doador.dto.mapper.DoadorMapper;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class DoadorService {

    private final DoadorRepository doadorRepository;

    public void cadastrar(DoadorCriacaoDTO doadorCriacaoDTO) {
        final Doador novoDoador = DoadorMapper.toEntity(doadorCriacaoDTO);
        this.doadorRepository.save(novoDoador);
    }

    public boolean login(DoadorLoginDTO doadorLoginDTO, DoadorRepository doadorRepository) {
        Doador doador = doadorRepository.findByEmail(doadorLoginDTO.getEmail());
        if (doador == null) {
            return false;
        }
        return doador.getSenha().equals(doadorLoginDTO.getSenha());
    }
}
