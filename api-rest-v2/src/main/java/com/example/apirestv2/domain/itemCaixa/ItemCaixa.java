package com.example.apirestv2.domain.itemCaixa;

import jakarta.persistence.*;

@Entity
@Table(name = "item_caixa", schema = "db_9solutions")
public class ItemCaixa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produto_caixa")
    private Integer id;

    @Column(name = "fk_caixa")
    private int idCaixa;

    @Column(name = "fk_produto")
    private int idProduto;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdCaixa() {
        return idCaixa;
    }

    public void setIdCaixa(int idCaixa) {
        this.idCaixa = idCaixa;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    @Override
    public String toString() {
        return "ItemCaixa{" +
                "id=" + id +
                ", idCaixa=" + idCaixa +
                ", idProduto=" + idProduto +
                '}';
    }
}
