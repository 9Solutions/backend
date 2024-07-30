package com.caixadesapato.api.repository;

import com.caixadesapato.api.model.EtapaCaixa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtapaCaixaRepository extends JpaRepository<EtapaCaixa, Integer> { }
