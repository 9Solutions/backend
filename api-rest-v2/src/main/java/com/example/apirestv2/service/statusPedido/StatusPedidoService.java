package com.example.apirestv2.service.statusPedido;

import com.example.apirestv2.domain.statusPedido.StatusPedido;
import com.example.apirestv2.domain.statusPedido.repository.StatusPedidoRepository;
import com.example.apirestv2.service.statusPedido.dto.StatusPedidoListagem;
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
