package com.example.apirestv2.domain.categoria.repository;

import com.example.apirestv2.domain.categoria.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}
