package com.caixadesapato.api.repository.view;

import com.caixadesapato.api.model.view.VwFiltroPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VwFiltrosPedidosRepository extends JpaRepository<VwFiltroPedido, Integer> {
    @Query("SELECT f FROM VwFiltroPedido f WHERE concat(f.idPedido, '') LIKE :idPedido AND concat(f.dataPedido, '') LIKE :dataPedido AND concat(f.idStatusPedido, '') LIKE :status")
    List<VwFiltroPedido> buscaFiltros(String idPedido, String dataPedido, String status);
}
