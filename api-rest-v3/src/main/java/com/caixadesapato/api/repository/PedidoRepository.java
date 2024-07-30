package com.caixadesapato.api.repository;

import com.caixadesapato.api.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    @Query("SELECT p FROM Pedido p WHERE concat(p.id, '') LIKE :idPedido AND concat(p.dataPedido, '') LIKE :dataPedido AND p.statusPedido.status LIKE :status")
    List<Pedido> buscaFiltros(String idPedido, String dataPedido, String status);
}
