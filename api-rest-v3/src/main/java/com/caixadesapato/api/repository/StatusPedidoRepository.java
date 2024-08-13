package com.caixadesapato.api.repository;

import com.caixadesapato.api.model.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusPedidoRepository extends JpaRepository<StatusPedido, Integer> { }
