package com.example.apirestv2.service.vwCaixasParaEntregar;

import com.example.apirestv2.domain.vwCaixasParaEntregar.repository.VwCaixasParaEntregarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VwCaixasParaEntregar {
    private final VwCaixasParaEntregarRepository caixasParaEntregarRepository;


}
