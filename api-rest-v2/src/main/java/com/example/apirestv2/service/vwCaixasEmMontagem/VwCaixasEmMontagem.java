package com.example.apirestv2.service.vwCaixasEmMontagem;

import com.example.apirestv2.domain.vwCaixasEmMontagem.repository.VwCaixasEmMontagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VwCaixasEmMontagem {
    private final VwCaixasEmMontagemRepository vwCaixasEmMontagemRepository;

    public List<com.example.apirestv2.domain.vwCaixasEmMontagem.VwCaixasEmMontagem> findAll() {
        return vwCaixasEmMontagemRepository.findAll();
    }
}
