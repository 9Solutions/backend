package com.example.apirestv2.domain.pedido.repository;

import com.example.apirestv2.domain.pedido.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByStatusPedidoEquals(Integer status);

}
