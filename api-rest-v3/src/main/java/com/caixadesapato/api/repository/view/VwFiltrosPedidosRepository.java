package com.caixadesapato.api.repository.view;

import com.caixadesapato.api.model.view.VwFiltroPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VwFiltrosPedidosRepository extends JpaRepository<VwFiltroPedido, Integer> {
    @Query("SELECT f FROM VwFiltroPedido f LEFT JOIN f.statusCaixa sc WHERE concat(f.id, '') LIKE :idPedido AND concat(f.dataPedido, '') LIKE :dataPedido AND concat(sc.status, '') LIKE :status")
    List<VwFiltroPedido> buscaFiltros(String idPedido, String dataPedido, String status);
}
