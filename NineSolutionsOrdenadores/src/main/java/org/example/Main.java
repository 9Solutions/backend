package org.example;

import org.example.item.Caixa;
import org.example.item.Item;

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
        Item item1 = new Item("carrinho", "carrinho de brinquedo", 40.);
        Item item2 = new Item("boneca", "boneca de pano", 50.);
        Item item3 = new Item("bola", "bola de futebol", 30.);
        Item item4 = new Item("jogo de tabuleiro", "jogo de tabuleiro Monopoly", 100.);
        Item item5 = new Item("livro", "livro Harry Potter", 60.);
        Item item6 = new Item("camiseta", "camiseta tamanho M", 25.);
        Item item7 = new Item("caneca", "caneca com estampa de gatinho", 35.);
        Item item8 = new Item("celular", "celular Samsung Galaxy S23", 2000.);
        Caixa caixa = new Caixa(1, "Aproveite o seu presente!\n" +
                "\n" +
                "Com muito carinho");
        caixa.adicionarProduto(item1);
        caixa.adicionarProduto(item2);
        caixa.adicionarProduto(item3);
        caixa.adicionarProduto(item4);
        caixa.adicionarProduto(item5);
        caixa.adicionarProduto(item6);
        caixa.adicionarProduto(item7);
        caixa.adicionarProduto(item8);
        Item[] items = {item1, item2, item3, item4, item5, item6, item7, item8};
//        insertionSort(produtos);
//        produtos = new Produto[]{produto1, produto2, produto3, produto4, produto5, produto6, produto7, produto8};
//        selectionSortOtimizado(produtos);
//        produtos = new Produto[]{produto1, produto2, produto3, produto4, produto5, produto6, produto7, produto8};
        bubbleSort(items);
        for (int i = 0; i < items.length; i++) {
            List<Item> itemList = Arrays.stream(items).toList();
            System.out.println("""
                    Preço: R$%.2f
                    """.formatted(itemList.get(i).getPreco()));
        }
    }
}