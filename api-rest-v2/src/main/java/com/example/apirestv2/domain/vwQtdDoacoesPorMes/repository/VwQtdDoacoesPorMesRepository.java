package com.example.apirestv2.domain.vwQtdDoacoesPorMes.repository;

import com.example.apirestv2.domain.vwQtdDoacoesPorMes.VwQtdDoacoesPorMes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VwQtdDoacoesPorMesRepository extends JpaRepository<VwQtdDoacoesPorMes, Integer> {
    List<VwQtdDoacoesPorMes> findByAnoAndMes(Integer ano, Integer mes);
}