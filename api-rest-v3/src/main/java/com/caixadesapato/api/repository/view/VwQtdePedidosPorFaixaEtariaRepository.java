package com.caixadesapato.api.repository.view;

import com.caixadesapato.api.model.view.VwQtdePedidosPorFaixaEtaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VwQtdePedidosPorFaixaEtariaRepository extends JpaRepository<VwQtdePedidosPorFaixaEtaria, Integer> {
    List<VwQtdePedidosPorFaixaEtaria> findByAno(Integer ano);
}
