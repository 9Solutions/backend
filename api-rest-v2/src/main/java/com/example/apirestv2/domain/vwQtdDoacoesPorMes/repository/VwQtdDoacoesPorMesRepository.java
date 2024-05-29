package com.example.apirestv2.domain.vwQtdDoacoesPorMes.repository;

import com.example.apirestv2.domain.vwQtdDoacoesPorMes.VwQtdDoacoesPorMes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VwQtdDoacoesPorMesRepository extends JpaRepository<VwQtdDoacoesPorMes, Integer> {

}