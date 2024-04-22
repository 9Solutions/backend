package com.example.apirestv2.domain.doador.repository;

import com.example.apirestv2.domain.doador.Doador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoadorRepository extends JpaRepository<Doador, Integer> {
    Doador findByEmail(String email);

    Doador findBySenha(String email);
}
