package com.example.apirestv2.domain.vwCaixasAtrasadas.repository;

import com.example.apirestv2.domain.vwCaixasAtrasadas.VwCaixasAtrasadas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VwCaixasAtrasadasRepository extends JpaRepository<VwCaixasAtrasadas, Integer> {
    List<VwCaixasAtrasadas> findAll();
}
