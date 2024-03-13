package org.example;

import org.example.produto.Caixa;
import org.example.produto.Produto;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static int[] selectionSort(int[] desordenados) {
        int[] ordenados = desordenados;
        Integer n = ordenados.length;
        Integer comparacao = 0;
        Integer troca = 0;

        for (int i = 0; i < n - 1; i++) {
            int minimo = i;
            troca++;
            for (int j = i + 1; j < n; j++) {
                if (ordenados[j] < ordenados[minimo]) {
                    int menorDaVez = ordenados[minimo];
                    ordenados[minimo] = ordenados[i];
                    ordenados[i] = menorDaVez;
                }
                comparacao++;
            }
        }

        System.out.println("QTD COMPARAÇOES: " + comparacao);
        System.out.println("QTD TROCAS: " + troca);

        return ordenados;
    }

    public static int[] selectionSortOtimizado(int[] desordenados) {
        int[] ordenados = desordenados;
        Integer n = ordenados.length;
        Integer comparacao = 0;
        Integer troca = 0;

        for (int i = 0; i < n - 1; i++) {
            int minimo = i;
            troca++;
            for (int j = i + 1; j < n; j++) {
                if (ordenados[j] < ordenados[minimo]) {
                    minimo = j;
                }
                comparacao++;
            }
            int menorDaVez = ordenados[minimo];
            ordenados[minimo] = ordenados[i];
            ordenados[i] = menorDaVez;
        }

        System.out.println("QTD COMPARAÇOES: " + comparacao);
        System.out.println("QTD TROCAS: " + troca);

        return ordenados;
    }

    public static void bubbleSort(int[] ordenado) {
        int comparacao = 0;
        int troca = 0;


        for (int i = 0; i < ordenado.length - 1; i++) {

            for (int j = 0; j < ordenado.length - i - 1; j++) {

                if (ordenado[j] > ordenado[j + 1]) {
                    int temp = ordenado[j];
                    ordenado[j] = ordenado[j + 1];
                    ordenado[j + 1] = temp;
                    troca++;
                }

                comparacao++;

            }

        }
    }

//    public static int[] insertionSort(int[] desordenados) {
//        int[] ordenados = desordenados;
//        Integer n = ordenados.length;
//        Integer comparacao = 0;
//        Integer troca = 0;
//
//        for (int i = 1; i < n; i++) {
//            int key = ordenados[i];
//            int j;
//            for (j = i - 1; j >= 0 && ordenados[j] > key; j--) {
//                ordenados[j + 1] = ordenados[j];
//            }
//            ordenados[j + 1] = key;
//        }
//
//        System.out.println("QTD COMPARAÇOES: " + comparacao);
//        System.out.println("QTD TROCAS: " + troca);
//
//        return ordenados;
//    }

    public static double[] insertionSort(double[] lista) {
        double[] ordenados = lista;
        Integer n = ordenados.length;

        for (int i = 1; i < n; i++) {
            double key = ordenados[i];
            int j;
            for (j = i - 1; j >= 0 && ordenados[j] > key; j--) {
                ordenados[j + 1] = ordenados[j];
            }
            ordenados[j + 1] = key;
        }

        return ordenados;
    }

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

        double[] precos = {
                produto1.getPreco(),
                produto2.getPreco(),
                produto3.getPreco(),
                produto4.getPreco(),
                produto5.getPreco(),
                produto6.getPreco(),
                produto7.getPreco(),
                produto8.getPreco()
        };
        Caixa caixa = new Caixa(1, "Aproveite o seu presente!\n" +
                "\n" +
                "Com muito carinho");
        insertionSort(precos);
        for (int i = 0; i < precos.length; i++) {
            System.out.println("""
                    Preço: R$%.2f
                    """.formatted(precos[i]));
        }
    }
}