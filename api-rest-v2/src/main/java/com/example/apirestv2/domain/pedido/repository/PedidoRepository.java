package com.example.apirestv2.domain.pedido.repository;

import com.example.apirestv2.domain.pedido.FiltroPedidos;
import com.example.apirestv2.domain.pedido.Pedido;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    @Query("SELECT p FROM Pedido p WHERE concat(p.id, '') LIKE :idPedido AND concat(p.dataPedido, '') LIKE :dataPedido AND p.statusPedido.status LIKE :status")
    List<Pedido> buscaFiltros(String idPedido, String dataPedido, String status);
}
