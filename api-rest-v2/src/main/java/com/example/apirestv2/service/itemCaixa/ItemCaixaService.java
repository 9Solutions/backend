package com.example.apirestv2.service.itemCaixa;

import com.example.apirestv2.domain.itemCaixa.ItemCaixa;
import com.example.apirestv2.domain.itemCaixa.repository.ItemCaixaRepository;
import com.example.apirestv2.service.itemCaixa.dto.ItemCaixaMapper;
import com.example.apirestv2.service.itemCaixa.dto.ItemsCaixaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
public class ItemCaixaService {

    @Autowired
    private ItemCaixaRepository action;

    public List<ItemCaixa> listByIdItemsCaixa(Integer id){
        List<ItemCaixa> lista = action.findByIdCaixaEquals(id);
        if(!lista.isEmpty()){
            return lista;
        }
        return null;
    }


    public boolean insertItems(int idCaixa, int[] itemsCaixa){

      List<ItemCaixa> listaItemsCaixa = this.transformArrayToListEntity(idCaixa, itemsCaixa);

      if(!listaItemsCaixa.isEmpty()){
          boolean madeInsertion = insertIntoDatabase(0, listaItemsCaixa);
          return madeInsertion ? true : false;
      }

      return false;
    }

    // Insere de forma recursiva os items no banco de dados
    private boolean insertIntoDatabase(
            int i, List<ItemCaixa> items
    ) {
       if(i < items.size()){
           action.save(items.get(i));
           insertIntoDatabase(i + 1, items);
       }
       return true;
    }

    // Tranformar o array recebido em uma lista de entidades do tipo ItemCaixa
    private List<ItemCaixa> transformArrayToListEntity(
            int idCaixa, int[] itemsCaixa
    ) {
        List<ItemCaixa> listaItemsCaixa = new ArrayList<>();

        for(int idProduto : itemsCaixa){
            ItemCaixa novoItem = new ItemCaixa();
            novoItem.setIdCaixa(idCaixa);

            listaItemsCaixa.add(novoItem);
        }
        return listaItemsCaixa;
    }

}
