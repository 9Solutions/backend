package com.caixadesapato.api.repository;

import com.caixadesapato.api.model.FaixaEtaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FaixaEtariaRepository extends JpaRepository<FaixaEtaria, Integer> {
    List<FaixaEtaria> findByCondicaoEquals(int condicao);
}
