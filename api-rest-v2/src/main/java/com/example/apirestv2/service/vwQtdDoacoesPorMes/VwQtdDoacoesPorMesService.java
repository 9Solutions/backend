package com.example.apirestv2.service.vwQtdDoacoesPorMes;

import com.example.apirestv2.domain.vwQtdDoacoesPorMes.VwQtdDoacoesPorMes;
import com.example.apirestv2.domain.vwQtdDoacoesPorMes.repository.VwQtdDoacoesPorMesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VwQtdDoacoesPorMesService {
    private final VwQtdDoacoesPorMesRepository vwQtdDoacoesPorMesRepository;

    public List<VwQtdDoacoesPorMes> findAll() {
        return vwQtdDoacoesPorMesRepository.findAll();
    }
}
