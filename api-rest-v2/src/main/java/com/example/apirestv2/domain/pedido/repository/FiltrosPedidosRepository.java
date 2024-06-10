package com.example.apirestv2.domain.pedido.repository;

import com.example.apirestv2.domain.pedido.FiltroPedidos;
import com.example.apirestv2.domain.pedido.Pedido;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FiltrosPedidosRepository extends JpaRepository<FiltroPedidos, Integer> {

    @Query("SELECT f FROM FiltroPedidos f WHERE f.idPedido LIKE :idPedido AND f.dataPedido LIKE :dataPedido AND f.statusPedido.status LIKE :status")
    List<FiltroPedidos> buscaFiltros(String idPedido, String dataPedido, String status);

}
