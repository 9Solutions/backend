package org.example.item;

import java.util.ArrayList;
import java.util.List;

public class Caixa {
    private int idCaixa;
    private String mensagem;
    private int idProduto = 0;
    private List<Item> items = new ArrayList<>();

    public Caixa(int idCaixa, String mensagem) {
        this.idCaixa = idCaixa;
        this.mensagem = mensagem;
    }

    public List<Item> getProdutos() {
        return items;
    }

    public void adicionarProduto(Item item){
        item.setId(++idProduto);
        items.add(item);
        System.out.println("Produto adicionado à Caixa");
    }

    public void exibirProdutos(){
        System.out.println("ID da Caixa: "+ idCaixa);
        for (Item item : items){
            System.out.println(item.toString());
        }
    }
}
