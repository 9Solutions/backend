package com.caixadesapato.api.repository.view;

import com.caixadesapato.api.model.view.VwQtdeDoacoesPorMes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VwQtdeDoacoesPorMesRepository extends JpaRepository<VwQtdeDoacoesPorMes, Integer> {
    List<VwQtdeDoacoesPorMes> findByAnoAndMes(Integer ano, Integer mes);
}
