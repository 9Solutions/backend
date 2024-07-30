package com.caixadesapato.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "item_caixa", schema = "db_9solutions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemCaixa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produto_caixa")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "fk_produto")
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "fk_caixa")
    private Caixa caixa;

}
