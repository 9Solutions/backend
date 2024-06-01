package com.example.apirestv2.domain.caixa.repository;

import com.example.apirestv2.domain.caixa.EtapaCaixa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtapaCaixaRepository extends JpaRepository<EtapaCaixa, Integer> { }
