package app.model;

public class Item {
    private int id_item;
    private String item_nome;
    private Double valor;

    public Item(int id_item, String item_nome, Double valor) {
        this.id_item = id_item;
        this.item_nome = item_nome;
        this.valor = valor;
    }

    public int getId_item() {
        return id_item;
    }

    public void setId_item(int id_item) {
        this.id_item = id_item;
    }

    public String getItem_nome() {
        return item_nome;
    }

    public void setItem_nome(String item_nome) {
        this.item_nome = item_nome;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
