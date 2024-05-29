package com.example.apirestv2.service.vwCaixasAtrasadas;

import com.example.apirestv2.domain.vwCaixasAtrasadas.VwCaixasAtrasadas;
import com.example.apirestv2.domain.vwCaixasAtrasadas.repository.VwCaixasAtrasadasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VwCaixasAtrasadasService {
    private final VwCaixasAtrasadasRepository vwCaixasAtrasadasRepository;

    public List<VwCaixasAtrasadas> findAll() {
        return vwCaixasAtrasadasRepository.findAll();
    }

}
