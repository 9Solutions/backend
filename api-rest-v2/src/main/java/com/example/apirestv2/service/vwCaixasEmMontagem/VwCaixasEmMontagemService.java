package com.example.apirestv2.service.vwCaixasEmMontagem;

import com.example.apirestv2.domain.vwCaixasEmMontagem.VwCaixasEmMontagem;
import com.example.apirestv2.domain.vwCaixasEmMontagem.repository.VwCaixasEmMontagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VwCaixasEmMontagemService {
    private final VwCaixasEmMontagemRepository vwCaixasEmMontagemRepository;

    public List<VwCaixasEmMontagem> findAll() {
        return vwCaixasEmMontagemRepository.findAll();
    }
}
