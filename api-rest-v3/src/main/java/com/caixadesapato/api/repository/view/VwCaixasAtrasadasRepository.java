package com.caixadesapato.api.repository.view;

import com.caixadesapato.api.model.view.VwCaixasAtrasadas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VwCaixasAtrasadasRepository extends JpaRepository<VwCaixasAtrasadas, Integer> {
    List<VwCaixasAtrasadas> findAll();
}
