package com.caixadesapato.api.repository;

import com.caixadesapato.api.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    List<Produto> findByAtivoIs(int i);

    @Query("SELECT p FROM Produto p WHERE ativo = 1")
    List<Produto> produtosAtivos();
}
