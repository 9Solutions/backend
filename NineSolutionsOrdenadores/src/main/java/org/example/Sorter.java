package org.example;

public abstract class Sorter {
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

    public static int[] insertionSort(int[] lista) {
        int[] ordenados = lista;
        Integer n = ordenados.length;

        for (int i = 1; i < n; i++) {
            int key = ordenados[i];
            int j;
            for (j = i - 1; j >= 0 && ordenados[j] > key; j--) {
                ordenados[j + 1] = ordenados[j];
            }
            ordenados[j + 1] = key;
        }

        return ordenados;
    }

    public static int[] reverseInsertionSort(int[] lista) {
        int[] ordenados = lista;
        Integer n = ordenados.length;

        for (int i = 1; i < n; i++) {
            int key = ordenados[i];
            int j = i - 1;
            while (j >= 0 && ordenados[j] < key) {
                ordenados[j + 1] = ordenados[j];
                j--;
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
}

