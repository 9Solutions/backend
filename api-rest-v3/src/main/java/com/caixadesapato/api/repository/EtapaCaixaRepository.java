package com.caixadesapato.api.repository;

import com.caixadesapato.api.model.EtapaCaixa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EtapaCaixaRepository extends JpaRepository<EtapaCaixa, Integer> {
    List<EtapaCaixa> findByCaixa_Id(int idCaixa);
}
