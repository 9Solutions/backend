package org.example.item;

public class Item {
    private int id;
    private String nome;
    private String descricao;
    private double preco;
    public Item() {
    }
    public Item(String nome, String descricao, double preco) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return """
                -----------------
                Produto
                Id: %d
                Nome: %s
                Descrição: %s
                Preço: %.2f
                """.formatted(id, nome, descricao, preco);
    }
}
