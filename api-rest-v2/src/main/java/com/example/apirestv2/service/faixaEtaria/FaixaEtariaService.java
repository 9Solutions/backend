package com.example.apirestv2.service.faixaEtaria;

import com.example.apirestv2.domain.faixaEtaria.FaixaEtaria;
import com.example.apirestv2.domain.faixaEtaria.repository.FaixaEtariaRepository;
import com.example.apirestv2.service.faixaEtaria.dto.FaixaEtariaCriacaoDTO;
import com.example.apirestv2.service.faixaEtaria.dto.FaixaEtariaListagemDTO;
import com.example.apirestv2.service.faixaEtaria.dto.FaixaEtariaMapper;
import com.example.apirestv2.service.faixaEtaria.dto.FaixaEtariaUpdateDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FaixaEtariaService {
    private final FaixaEtariaRepository faixaEtariaRepository;

    public ResponseEntity<List<FaixaEtariaListagemDTO>> listAll(){
        List<FaixaEtaria> faixaEtarias = faixaEtariaRepository.findAll();

        if(faixaEtarias.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        List<FaixaEtariaListagemDTO> dto = FaixaEtariaMapper.toDTO(faixaEtarias);
        return ResponseEntity.status(200).body(dto);
    }

    public ResponseEntity<FaixaEtariaListagemDTO> findById(int id){
        Optional<FaixaEtaria> faixaEtarias = faixaEtariaRepository.findById(id);

        if(faixaEtarias.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        FaixaEtariaListagemDTO dto = FaixaEtariaMapper.toDTO(faixaEtarias.get());
        return ResponseEntity.ok().body(dto);
    }

    public ResponseEntity<FaixaEtariaListagemDTO> create(FaixaEtariaCriacaoDTO faixaEtariaCriacaoDTO){
        if(faixaEtariaCriacaoDTO.checkLimite()){
            return ResponseEntity.badRequest().build();
        }

        FaixaEtaria faixaEtaria = FaixaEtariaMapper.toEntity(faixaEtariaCriacaoDTO);

        FaixaEtaria faixaEtariaSalva = faixaEtariaRepository.save(faixaEtaria);

        FaixaEtariaListagemDTO dto = FaixaEtariaMapper.toDTO(faixaEtariaSalva);

        return ResponseEntity.status(201).body(dto);
    }

    public ResponseEntity<FaixaEtariaListagemDTO> update(int id, FaixaEtariaUpdateDTO faixaEtariaUpdateDTO){
        if(!faixaEtariaRepository.existsById(id)){
            return ResponseEntity.noContent().build();
        }

        FaixaEtaria faixaEtaria = FaixaEtariaMapper.toEntity(faixaEtariaUpdateDTO);
        faixaEtaria.setId(id);

        faixaEtariaRepository.save(faixaEtaria);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Void> delete(int id){
        if(!faixaEtariaRepository.existsById(id)) {
            return ResponseEntity.noContent().build();
        }

        faixaEtariaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
