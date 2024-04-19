package com.example.apirestv2.domain.caixa.repository;

import com.example.apirestv2.domain.caixa.Caixa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaixaRepository extends JpaRepository<Caixa, Integer> {
}
