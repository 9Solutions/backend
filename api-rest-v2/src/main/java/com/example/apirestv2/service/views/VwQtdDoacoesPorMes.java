package com.example.apirestv2.service.views;

import com.example.apirestv2.domain.vwQtdDoacoesPorMes.repository.VwQtdDoacoesPorMesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VwQtdDoacoesPorMes {
    private final VwQtdDoacoesPorMesRepository vwQtdDoacoesPorMesRepository;
}
