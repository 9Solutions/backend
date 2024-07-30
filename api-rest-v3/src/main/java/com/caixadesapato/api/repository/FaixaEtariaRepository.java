package com.caixadesapato.api.repository;

import com.caixadesapato.api.model.FaixaEtaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaixaEtariaRepository extends JpaRepository<FaixaEtaria, Integer> { }
