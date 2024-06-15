package com.example.apirestv2.domain.doador.repository;

import com.example.apirestv2.domain.doador.Doador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoadorRepository extends JpaRepository<Doador, Long> {
    Optional<Doador> findByEmail(String email);
}
