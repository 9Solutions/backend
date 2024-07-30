package com.caixadesapato.api.repository;

import com.caixadesapato.api.model.Caixa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaixaRepository extends JpaRepository<Caixa, Integer> {
    List<Caixa> findAll();
}
