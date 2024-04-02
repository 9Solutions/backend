package app.repository;

import app.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    Optional<Item> findById(Integer id);

    void deleteById(Integer id);

    List<Item> findAll();

    // Contar o número total de itens
    long count();

    boolean existsById(Integer id);

}