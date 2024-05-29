package com.example.apirestv2.domain.vwCaixasParaEntregar.repository;

import com.example.apirestv2.domain.vwCaixasParaEntregar.VwCaixasParaEntregar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VwCaixasParaEntregarRepository extends JpaRepository<VwCaixasParaEntregar, Integer> {

}