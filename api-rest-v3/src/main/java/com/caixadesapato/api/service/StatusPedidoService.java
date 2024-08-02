package com.caixadesapato.api.service;

import com.caixadesapato.api.model.StatusPedido;
import com.caixadesapato.api.repository.StatusPedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class StatusPedidoService {
    private final StatusPedidoRepository statusPedidoRepository;

    public StatusPedido findById(Integer id) {
        return statusPedidoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não Encontrado"));
    }
}
