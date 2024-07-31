package com.caixadesapato.api.service;

import com.caixadesapato.api.dto.categoria.CategoriaUpdateDTO;
import com.caixadesapato.api.model.Categoria;
import com.caixadesapato.api.repository.CategoriaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoriaService {

    @Autowired
    private CategoriaRepository action;

    public List<Categoria> listAll(){
        return action.findAll();
    }

    public Categoria findById(Integer id){
        return action.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada")
        );
    }

    public Categoria create(Categoria categoriaNova){ return action.save(categoriaNova); }

    public Categoria update(Integer id, CategoriaUpdateDTO categoriaAtualizada){
        Categoria categoria = action.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada")
        );

        categoria.setNome(categoriaAtualizada.getNome());
        return action.save(categoria);
    }

    public Categoria delete(Integer id) {
        Categoria categoria = action.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada")
        );

        action.delete(categoria);
        return categoria;
    }

}
