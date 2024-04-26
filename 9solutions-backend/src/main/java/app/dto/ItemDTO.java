package app.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class ItemDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_item;
    @NotBlank(message = "O nome do item não pode estar em branco")
    private String item_nome;

    @NotNull(message = "O valor do item não pode ser nulo")
    @Positive(message = "O valor do item deve ser positivo")
    private Double valor;

    public ItemDTO(int id_item, String item_nome, Double valor) {
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
