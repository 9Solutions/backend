package com.example.apirestv2.domain.itemCaixa.repository;

import com.example.apirestv2.domain.itemCaixa.ItemCaixa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemCaixaRepository extends JpaRepository<ItemCaixa, Integer> {

//    List<ItemCaixa> findByIdCaixaEquals(Integer id);
}
