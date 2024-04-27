package com.example.apirestv2.domain.pedido.repository;

import com.example.apirestv2.domain.pedido.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
}
