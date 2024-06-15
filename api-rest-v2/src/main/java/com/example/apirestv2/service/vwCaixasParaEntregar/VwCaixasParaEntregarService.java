package com.example.apirestv2.service.vwCaixasParaEntregar;

import com.example.apirestv2.domain.vwCaixasParaEntregar.VwCaixasParaEntregar;
import com.example.apirestv2.domain.vwCaixasParaEntregar.repository.VwCaixasParaEntregarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VwCaixasParaEntregarService {
    private final VwCaixasParaEntregarRepository caixasParaEntregarRepository;

    public List<VwCaixasParaEntregar> findAll() {
        return caixasParaEntregarRepository.findAll();
    }
}
