package org.example.utils;

import org.example.item.Item;

public abstract class Sorter {
    public static void bubbleSort(Item[] ordenado) {
        int comparacao = 0;
        int troca = 0;

        for (int i = 0; i < ordenado.length - 1; i++) {
            for (int j = 0; j < ordenado.length - i - 1; j++) {
                if (ordenado[j].getPreco() > ordenado[j + 1].getPreco()) {
                    Item temp = ordenado[j];
                    ordenado[j] = ordenado[j + 1];
                    ordenado[j + 1] = temp;
                    troca++;
                }
                comparacao++;
            }
        }
    }
    public static void selectionSortOtimizado(Item[] desordenados) {
        Item[] ordenados = desordenados;
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
            Item menorDaVez = ordenados[minimo];
            ordenados[minimo] = ordenados[i];
            ordenados[i] = menorDaVez;
        }

//        System.out.println("QTD COMPARAÇOES: " + comparacao);
//        System.out.println("QTD TROCAS: " + troca);
    }

    public static void insertionSort(Item[] lista) {
        Item[] ordenados = lista;
        Integer n = ordenados.length;

        for (int i = 1; i < n; i++) {
            Item key = ordenados[i];
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

