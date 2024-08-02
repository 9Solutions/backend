package com.caixadesapato.api.service;

import com.caixadesapato.api.model.Caixa;
import com.caixadesapato.api.model.ItemCaixa;
import com.caixadesapato.api.model.Produto;
import com.caixadesapato.api.repository.ItemCaixaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemCaixaService {

    private final ItemCaixaRepository action;

    private final ProdutoService produtoService;

    public List<ItemCaixa> insertItems(Caixa caixa, int[] itemsCaixa){

        List<ItemCaixa> listaItemsCaixa = this.transformArrayToListEntity(caixa, itemsCaixa);

        if(!listaItemsCaixa.isEmpty()){
            boolean madeInsertion = insertIntoDatabase(0, listaItemsCaixa);
        }

        return listaItemsCaixa;
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
            Caixa caixa, int[] itemsCaixa
    ) {
        List<ItemCaixa> listaItemsCaixa = new ArrayList<>();

        for(int idProduto : itemsCaixa){

            Produto produto = produtoService.findById(idProduto);

            ItemCaixa novoItem = new ItemCaixa();
            novoItem.setCaixa(caixa);
            novoItem.setProduto(produto);
            listaItemsCaixa.add(novoItem);
        }
        return listaItemsCaixa;
    }

}
