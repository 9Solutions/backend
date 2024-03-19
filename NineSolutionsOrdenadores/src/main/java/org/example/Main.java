package org.example;

import org.example.produto.Caixa;
import org.example.produto.Produto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.example.utils.Sorter.*;

public class Main {

    public static Integer pesqBinaria(int[] ordenados, int indice) {

        int indInicio = 0;
        int indFinal = ordenados.length - 1;

        while (indInicio <= indFinal) {
            int indMeio = indInicio + (indFinal - indInicio) / 2;

            if (ordenados[indMeio] == indice) {
                return indMeio;
            }

            if (ordenados[indMeio] < indice) {
                indInicio = indMeio + 1;
            } else {
                indFinal = indMeio - 1;
            }
        }

        return -1;
    }
    public static void main(String[] args) {
        Produto produto1 = new Produto("carrinho", "carrinho de brinquedo", 40.);
        Produto produto2 = new Produto("boneca", "boneca de pano", 50.);
        Produto produto3 = new Produto("bola", "bola de futebol", 30.);
        Produto produto4 = new Produto("jogo de tabuleiro", "jogo de tabuleiro Monopoly", 100.);
        Produto produto5 = new Produto("livro", "livro Harry Potter", 60.);
        Produto produto6 = new Produto("camiseta", "camiseta tamanho M", 25.);
        Produto produto7 = new Produto("caneca", "caneca com estampa de gatinho", 35.);
        Produto produto8 = new Produto("celular", "celular Samsung Galaxy S23", 2000.);
        Caixa caixa = new Caixa(1, "Aproveite o seu presente!\n" +
                "\n" +
                "Com muito carinho");
        caixa.adicionarProduto(produto1);
        caixa.adicionarProduto(produto2);
        caixa.adicionarProduto(produto3);
        caixa.adicionarProduto(produto4);
        caixa.adicionarProduto(produto5);
        caixa.adicionarProduto(produto6);
        caixa.adicionarProduto(produto7);
        caixa.adicionarProduto(produto8);
        Produto[] produtos = {produto1, produto2, produto3, produto4, produto5, produto6, produto7, produto8};
//        insertionSort(produtos);
//        produtos = new Produto[]{produto1, produto2, produto3, produto4, produto5, produto6, produto7, produto8};
//        selectionSortOtimizado(produtos);
//        produtos = new Produto[]{produto1, produto2, produto3, produto4, produto5, produto6, produto7, produto8};
        bubbleSort(produtos);
        for (int i = 0; i < produtos.length; i++) {
            List<Produto> produtoList = Arrays.stream(produtos).toList();
            System.out.println("""
                    Preço: R$%.2f
                    """.formatted(produtoList.get(i).getPreco()));
        }
    }
}