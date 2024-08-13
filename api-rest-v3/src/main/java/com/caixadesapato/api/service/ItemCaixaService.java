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
        List<ItemCaixa> listaItemsCaixa = new ArrayList<ItemCaixa>();
        for(int idProduto : itemsCaixa){
            Produto produto = produtoService.findById(idProduto);
            ItemCaixa novoItem = new ItemCaixa();
            novoItem.setCaixa(caixa);
            novoItem.setProduto(produto);
            listaItemsCaixa.add(novoItem);
        }
        return action.saveAll(listaItemsCaixa);
    }

}
