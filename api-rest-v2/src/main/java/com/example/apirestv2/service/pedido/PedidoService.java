package com.example.apirestv2.service.pedido;

import com.example.apirestv2.domain.doador.Doador;
import com.example.apirestv2.domain.pedido.FiltroPedidos;
import com.example.apirestv2.domain.pedido.Pedido;
import com.example.apirestv2.domain.pedido.repository.FiltrosPedidosRepository;
import com.example.apirestv2.domain.pedido.repository.PedidoRepository;
import com.example.apirestv2.domain.statusPedido.StatusPedido;
import com.example.apirestv2.service.doador.DoadorService;
import com.example.apirestv2.service.interfaces.PublisherChange;
import com.example.apirestv2.service.pedido.dto.PedidoPatchDTO;
import com.example.apirestv2.service.statusPedido.StatusPedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService implements PublisherChange {

    private final PedidoRepository action;
    private final DoadorService doadorService;
    private final StatusPedidoService statusPedidoService;

    private final FiltrosPedidosRepository actionFiltro;

    public List<Pedido> listAll(){
        return action.findAll();
    }

    public Pedido listById(Integer id){
        return action.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Não encontrado")
        );
    }

    public List<Pedido> listByStatus(String status, String data, String idPedido){
        if(status == null || status.isBlank()) status = "%";
        if(data == null || data.isBlank()){
            data = "%";
        } else{
            data = data + "%";
        }
        if(idPedido == null || idPedido.isBlank()) idPedido = "%";
        return action.buscaFiltros(idPedido, data, status);
    }

    public Pedido create(Pedido novoPedido, Long idDoador){
        Doador doador = doadorService.buscarPorId(idDoador);
        StatusPedido statusPedido = statusPedidoService.findById(novoPedido.getStatusPedido().getId());
        novoPedido.setDoador(doador);
        novoPedido.setStatusPedido(statusPedido);
        return action.save(novoPedido);
    }

    public Pedido statusChange(Integer id, PedidoPatchDTO change){
        Pedido pedido = action.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não Encontrado")
        );

        this.notifyChange(pedido.getDoador());

        pedido.setStatusPedido(statusPedidoService.findById(change.getStatusChange()));
        return action.save(pedido);
    }

    @Override
    public void notifyChange(Doador entity) {
        doadorService.updateListener(entity.getEmail(), "Pedido");
    }

}
