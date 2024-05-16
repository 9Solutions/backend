package com.example.apirestv2.service.categoria;

import com.example.apirestv2.domain.categoria.Categoria;
import com.example.apirestv2.domain.categoria.repository.CategoriaRepository;
import com.example.apirestv2.service.categoria.dto.CategoriaCriacaoDTO;
import com.example.apirestv2.service.categoria.dto.CategoriaListagemDTO;
import com.example.apirestv2.service.categoria.dto.CategoriaMapper;
import com.example.apirestv2.service.categoria.dto.CategoriaUpdateDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoriaService {

    @Autowired
    private CategoriaRepository action;

    public List<Categoria> listAll(){ return action.findAll(); }

    public Categoria findById(Integer id){
        return action.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado")
        );
    }

    public Categoria create(Categoria categoriaNova){ return action.save(categoriaNova); }

    public Categoria update(Integer id, CategoriaUpdateDTO categoriaAtualizada){
        Categoria categoria = action.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado")
        );

        categoria.setNome(categoriaAtualizada.getNome());
        return action.save(categoria);
    }

    public Categoria delete(Integer id) {
        Categoria categoria = action.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado")
        );

        action.delete(categoria);
        return categoria;
    }
}