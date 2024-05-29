package com.example.apirestv2.domain.vwCaixasEmMontagem.repository;

import com.example.apirestv2.domain.vwCaixasEmMontagem.VwCaixasEmMontagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VwCaixasEmMontagemRepository extends JpaRepository<VwCaixasEmMontagem, Integer> {

}