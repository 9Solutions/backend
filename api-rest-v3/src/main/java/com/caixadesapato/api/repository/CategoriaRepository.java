package com.caixadesapato.api.repository;

import com.caixadesapato.api.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    List<Categoria> findAll();
    List<Categoria> findByEstagioEqualsAndCondicaoEquals(int estagio, int condicao);
    List<Categoria> findByCondicaoEquals(int condicao);
}
