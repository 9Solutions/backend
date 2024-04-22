package com.example.apirestv2.service.categoria;

import com.example.apirestv2.domain.categoria.Categoria;
import com.example.apirestv2.domain.categoria.repository.CategoriaRepository;
import com.example.apirestv2.service.categoria.dto.CategoriaCriacaoDTO;
import com.example.apirestv2.service.categoria.dto.CategoriaListagemDTO;
import com.example.apirestv2.service.categoria.dto.CategoriaMapper;
import com.example.apirestv2.service.categoria.dto.CategoriaUpdateDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    public ResponseEntity<List<CategoriaListagemDTO>> listAll(){
        List<Categoria> categorias = categoriaRepository.findAll();

        if(categorias.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        List<CategoriaListagemDTO> dto = CategoriaMapper.toDTO(categorias);
        return ResponseEntity.ok().body(dto);
    }

    public ResponseEntity<CategoriaListagemDTO> findById(int id){
        Optional<Categoria> categorias = categoriaRepository.findById(id);

        if(categorias.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        CategoriaListagemDTO dto = CategoriaMapper.toDTO(categorias.get());
        return ResponseEntity.ok().body(dto);
    }

    public ResponseEntity<CategoriaListagemDTO> create(CategoriaCriacaoDTO categoriaCriacaoDTO){
        Categoria categoria = CategoriaMapper.toEntity(categoriaCriacaoDTO);

        Categoria categoriaSalva = categoriaRepository.save(categoria);

        CategoriaListagemDTO dto = CategoriaMapper.toDTO(categoriaSalva);

        return ResponseEntity.created(null).body(dto);
    }

    public ResponseEntity<CategoriaListagemDTO> update(int id, CategoriaUpdateDTO categoriaUpdateDTO){
        if(!categoriaRepository.existsById(id)){
            return ResponseEntity.noContent().build();
        }

        Categoria categoria = CategoriaMapper.toEntity(categoriaUpdateDTO);
        categoria.setId(id);

        categoriaRepository.save(categoria);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Void> delete(int id){
        if(!categoriaRepository.existsById(id)) {
            return ResponseEntity.noContent().build();
        }

        categoriaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
