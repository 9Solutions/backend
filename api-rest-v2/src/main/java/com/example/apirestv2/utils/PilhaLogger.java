package com.example.apirestv2.utils;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class PilhaLogger {
    private static int topo;
    private File pilha;
    private Scanner reader;


    public PilhaLogger() {
        topo = 0;
        this.pilha = new File("log.txt");
        try {
            this.reader = new Scanner(this.pilha);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void push(String mensagem) {
        try {
            FileWriter writer = new FileWriter(this.pilha, true);
            writer.write(mensagem + "\n");
            writer.close();

            topo++;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String peek(){
        try {
            String log = "";

            for(int i = 0; i < this.topo; i++){
                log = this.reader.nextLine();
            }

            return log;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public void print() {
        try {
            while (this.reader.hasNextLine()) {
                System.out.println(this.reader.nextLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
