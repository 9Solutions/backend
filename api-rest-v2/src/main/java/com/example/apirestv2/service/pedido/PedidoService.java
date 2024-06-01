package com.example.apirestv2.service.pedido;

import com.example.apirestv2.domain.doador.Doador;
import com.example.apirestv2.domain.pedido.Pedido;
import com.example.apirestv2.domain.pedido.repository.PedidoRepository;
import com.example.apirestv2.service.doador.DoadorService;
import com.example.apirestv2.service.interfaces.ChangeListener;
import com.example.apirestv2.service.interfaces.PublisherChange;
import com.example.apirestv2.service.pedido.dto.PedidoCriacaoDTO;
import com.example.apirestv2.service.pedido.dto.PedidoMapper;
import com.example.apirestv2.service.pedido.dto.PedidoPatchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PedidoService implements PublisherChange {

    private final PedidoRepository action;
    private final DoadorService doadorService;

    public List<Pedido> listAll(){
        return action.findAll();
    }

    public Pedido listById(Integer id){
        return action.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Não encontrado")
        );
    }

    public List<Pedido> listByStatus(Integer status){
        return action.findByStatusPedidoEquals(status);
    }

    public Pedido create(Pedido novoPedido, Long idDoador){
        Doador doador = doadorService.buscarPorId(idDoador);
        novoPedido.setDoador(doador);
        return action.save(novoPedido);
    }

    public Pedido statusChange(Integer id, PedidoPatchDTO change){
        Pedido pedido = action.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não Encontrado")
        );

        this.notifyChange(pedido.getDoador());

        pedido.setStatusPedido(change.getStatusChange());
        return action.save(pedido);
    }

    @Override
    public void notifyChange(Doador entity) {
        doadorService.updateListener(entity.getEmail(), "Pedido");
    }

}
