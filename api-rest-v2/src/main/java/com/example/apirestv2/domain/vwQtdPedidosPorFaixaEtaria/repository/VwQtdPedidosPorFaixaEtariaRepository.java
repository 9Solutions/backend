package com.example.apirestv2.domain.vwQtdPedidosPorFaixaEtaria.repository;

import com.example.apirestv2.domain.vwQtdPedidosPorFaixaEtaria.VwQtdPedidosPorFaixaEtaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VwQtdPedidosPorFaixaEtariaRepository extends JpaRepository<VwQtdPedidosPorFaixaEtaria, Integer> {
    List<VwQtdPedidosPorFaixaEtaria> findByAno(Integer ano);
}