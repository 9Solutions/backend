package app.repository;

import app.dto.CaixaDTO;
import app.model.Caixa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CaixaRepository extends JpaRepository<CaixaDTO, Integer> {

    List<CaixaDTO> findAllByOrderByValorAsc();

    Optional<CaixaDTO> findByValor(double valor);

    Optional<CaixaDTO> findById(Long id);

    void deleteById(Integer id);

    List<CaixaDTO> findAll();

    // Contar o número total de caixas
    long count();

    boolean existsById(Integer id);

    List<CaixaDTO> findByNomeContainingIgnoreCase(String nome);

    List<CaixaDTO> findByDataLancamentoAfter(LocalDate dataLancamento);
}
