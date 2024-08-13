package com.caixadesapato.api.repository;

import com.caixadesapato.api.model.StatusCaixa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusCaixaRepository extends JpaRepository<StatusCaixa, Integer> {
}
