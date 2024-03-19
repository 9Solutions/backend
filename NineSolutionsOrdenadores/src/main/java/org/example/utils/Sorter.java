package org.example.utils;

import org.example.produto.Produto;

public abstract class Sorter {
    public static void bubbleSort(Produto[] ordenado) {
        int comparacao = 0;
        int troca = 0;

        for (int i = 0; i < ordenado.length - 1; i++) {
            for (int j = 0; j < ordenado.length - i - 1; j++) {
                if (ordenado[j].getPreco() > ordenado[j + 1].getPreco()) {
                    Produto temp = ordenado[j];
                    ordenado[j] = ordenado[j + 1];
                    ordenado[j + 1] = temp;
                    troca++;
                }
                comparacao++;
            }
        }
    }
    public static void selectionSortOtimizado(Produto[] desordenados) {
        Produto[] ordenados = desordenados;
        Integer n = ordenados.length;
        Integer comparacao = 0;
        Integer troca = 0;

        for (int i = 0; i < n - 1; i++) {
            int minimo = i;
            troca++;
            for (int j = i + 1; j < n; j++) {
                if (ordenados[j].getPreco() < ordenados[minimo].getPreco()) {
                    minimo = j;
                }
                comparacao++;
            }
            Produto menorDaVez = ordenados[minimo];
            ordenados[minimo] = ordenados[i];
            ordenados[i] = menorDaVez;
        }

//        System.out.println("QTD COMPARAÇOES: " + comparacao);
//        System.out.println("QTD TROCAS: " + troca);
    }

    public static void insertionSort(Produto[] lista) {
        Produto[] ordenados = lista;
        Integer n = ordenados.length;

        for (int i = 1; i < n; i++) {
            Produto key = ordenados[i];
            int j;
            for (j = i - 1; j >= 0 && ordenados[j].getPreco() > key.getPreco(); j--) {
                ordenados[j + 1] = ordenados[j];
            }
            ordenados[j + 1] = key;
        }
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
}

