package com.example.apirestv2.service.itemCaixa;

import com.example.apirestv2.domain.caixa.Caixa;
import com.example.apirestv2.domain.itemCaixa.ItemCaixa;
import com.example.apirestv2.domain.itemCaixa.repository.ItemCaixaRepository;
import com.example.apirestv2.domain.produto.Produto;
import com.example.apirestv2.service.itemCaixa.dto.ItemCaixaMapper;
import com.example.apirestv2.service.itemCaixa.dto.ItemsCaixaDTO;
import com.example.apirestv2.service.pedido.PedidoService;
import com.example.apirestv2.service.produto.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
public class ItemCaixaService {

    @Autowired
    private ItemCaixaRepository action;

    @Autowired
    private ProdutoService produtoService;


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
