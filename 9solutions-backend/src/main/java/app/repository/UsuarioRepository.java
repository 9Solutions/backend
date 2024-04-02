package app.repository;

import app.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findById(Long id);

    Optional<Usuario> findByEmail(String email);

    void deleteById(Long id);

    List<Usuario> findAll();

    // Contar o número total de usuários
    long count();

    boolean existsById(Long id);

    boolean existsByEmail(String email);

}
