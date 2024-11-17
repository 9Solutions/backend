package com.caixadesapato.api.repository;

import com.caixadesapato.api.model.Doador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoadorRepository extends JpaRepository<Doador, Long> {
    Optional<Doador> findByEmail(String email);
    List<Doador> findAllByPermissaoEqualsIgnoreCase(String permissao);
    Optional<Doador> findByIdentificador(String cpf);
}
