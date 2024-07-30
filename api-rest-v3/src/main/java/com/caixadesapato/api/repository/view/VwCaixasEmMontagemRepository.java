package com.caixadesapato.api.repository.view;

import com.caixadesapato.api.model.view.VwCaixasEmMontagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VwCaixasEmMontagemRepository extends JpaRepository<VwCaixasEmMontagem, Integer> { }
