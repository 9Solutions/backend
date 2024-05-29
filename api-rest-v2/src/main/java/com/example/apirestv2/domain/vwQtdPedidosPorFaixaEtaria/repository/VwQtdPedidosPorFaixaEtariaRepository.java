package com.example.apirestv2.domain.vwQtdPedidosPorFaixaEtaria.repository;

import com.example.apirestv2.domain.vwQtdPedidosPorFaixaEtaria.VwQtdPedidosPorFaixaEtaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VwQtdPedidosPorFaixaEtariaRepository extends JpaRepository<VwQtdPedidosPorFaixaEtaria, Integer> {
}