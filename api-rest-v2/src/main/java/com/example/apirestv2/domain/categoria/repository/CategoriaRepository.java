package com.example.apirestv2.domain.categoria.repository;

import com.example.apirestv2.domain.categoria.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    List<Categoria> findAll();
}