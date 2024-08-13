package com.caixadesapato.api.repository.view;

import com.caixadesapato.api.model.view.VwCaixasParaEntregar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VwCaixasParaEntregarRepository extends JpaRepository<VwCaixasParaEntregar, Integer> { }
