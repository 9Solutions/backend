package com.example.apirestv2.service.rotina;

public class FilaObj<T> {

    private int tamanho;
    private T[] fila;

    public FilaObj(int capaciade) {
        tamanho = 0;
        fila = (T[]) new Object[capaciade];
    }

    public boolean isEmpty() {
        return this.tamanho == 0;
    }

    public boolean isFull() {
        return tamanho == fila.length;
    }

    public void insert(T info) {
        if(isFull()) throw new IllegalStateException("Fila cheia");
        fila[tamanho] = info;
        tamanho++;
    }

    public T peek() {
        return fila[0];
    }

    public T poll() {
        T valorDaPrimeiraPosicao = null;
        if(!isEmpty()){
            valorDaPrimeiraPosicao = fila[0];
            for (int i = 0; i < tamanho - 1; i++) {
                fila[i] = fila[i + 1];
            }
            fila[tamanho-1] = null;
            tamanho--;
        }
        return valorDaPrimeiraPosicao;
    }

    public void exibe() {
        for (int i = 0; i < tamanho - 1; i++) {
            System.out.println(fila[i]);
        }
    }

    /* Usado nos testes  - complete para que fique certo */
    public int getTamanho(){
        return tamanho;
    }
}
