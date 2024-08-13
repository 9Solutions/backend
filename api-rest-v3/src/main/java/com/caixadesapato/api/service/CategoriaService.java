package com.caixadesapato.api.service;

import com.caixadesapato.api.dto.categoria.CategoriaUpdateDTO;
import com.caixadesapato.api.model.Categoria;
import com.caixadesapato.api.repository.CategoriaRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository action;

    public List<Categoria> listAll(){
        return action.findAll();
    }

    public Categoria findById(Integer id){
        return action.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada")
        );
    }

    public List<Categoria> findByParams(Integer estagio, Integer condicao) {
        if (estagio == null) {
            return action.findByCondicaoEquals(condicao);
        }
        return action.findByEstagioEqualsAndCondicaoEquals(estagio, condicao);
    }

    public Categoria create(Categoria categoriaNova){ return action.save(categoriaNova); }

    public Categoria update(Integer id, CategoriaUpdateDTO categoriaAtualizada){
        Categoria categoria = action.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada")
        );
        categoria.setNome(categoriaAtualizada.getNome());
        categoria.setEstagio(categoriaAtualizada.getEstagio());
        categoria.setQtdeProdutos(categoriaAtualizada.getQtdeProdutos());
        return action.save(categoria);
    }

    public Categoria changeStatus(Integer id, Integer condicao) {
        Categoria categoria = action.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada")
        );
        categoria.setCondicao(condicao);
        return action.save(categoria);
    }

}
