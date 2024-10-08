package com.example.apirestv2.domain.produto.repository;

import com.example.apirestv2.domain.produto.Produto;
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
