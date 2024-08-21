package com.caixadesapato.api.service;

import com.caixadesapato.api.dto.pedido.PedidoPatchDTO;
import com.caixadesapato.api.model.Doador;
import com.caixadesapato.api.model.Pedido;
import com.caixadesapato.api.model.StatusPedido;
import com.caixadesapato.api.model.view.VwFiltroPedido;
import com.caixadesapato.api.repository.PedidoRepository;
import com.caixadesapato.api.repository.view.VwFiltrosPedidosRepository;
import com.caixadesapato.api.utils.interfaces.PublisherChange;
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
    private final VwFiltrosPedidosRepository actionFiltro;

    public List<Pedido> listAll(){
        return action.findAll();
    }

    public Pedido listById(Integer id){
        return action.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Não encontrado")
        );
    }

    public List<Pedido> listAllDetailsByStatus(Integer statusPedido){
        return action.findByStatusPedido_IdEquals(statusPedido);
    }

    public List<VwFiltroPedido> listByStatus(String status, String data, String idPedido){
        if(status == null || status.isBlank()) status = "%";
        if(data == null || data.isBlank()){
            data = "%";
        } else{
            data = data + "%";
        }
        if(idPedido == null || idPedido.isBlank()) idPedido = "%";
        return actionFiltro.buscaFiltros(idPedido, data, status);
    }

    public Pedido create(Pedido novoPedido, Long idDoador){
        Doador doador = doadorService.buscarPorId(idDoador);
        StatusPedido statusPedido = statusPedidoService.findById(novoPedido.getStatusPedido().getId());
        novoPedido.setDoador(doador);
        novoPedido.setStatusPedido(statusPedido);
        return action.save(novoPedido);
    }

    public Pedido statusChange(Integer id, Integer condicao){
        Pedido pedido = action.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não Encontrado")
        );

        StatusPedido status = statusPedidoService.findById(condicao);
        pedido.setStatusPedido(status);
        this.notifyChange(pedido.getDoador());

        return action.save(pedido);
    }

    public List<Pedido> listAllDetailsByDoador(Integer idDoador) {
        return action.findByDoadorId(idDoador);
    }

    @Override
    public void notifyChange(Doador entity) {
        doadorService.updateListener(entity.getEmail(), "Pedido");
    }


}
