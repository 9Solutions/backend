package org.example.produto;

import java.util.ArrayList;
import java.util.List;

public class Caixa {
    private int idCaixa;
    private String mensagem;
    private int idProduto = 0;
    private List<Produto> produtos = new ArrayList<>();

    public Caixa(int idCaixa, String mensagem) {
        this.idCaixa = idCaixa;
        this.mensagem = mensagem;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void adicionarProduto(Produto produto){
        produto.setId(++idProduto);
        produtos.add(produto);
        System.out.println("Produto adicionado à Caixa");
    }

    public void exibirProdutos(){
        System.out.println("ID da Caixa: "+ idCaixa);
        for (Produto produto : produtos){
            System.out.println(produto.toString());
        }
    }
}
