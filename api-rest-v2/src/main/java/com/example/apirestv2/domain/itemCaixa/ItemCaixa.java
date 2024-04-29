package com.example.apirestv2.domain.itemCaixa;

import com.example.apirestv2.domain.caixa.Caixa;
import com.example.apirestv2.domain.produto.Produto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

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

    @Column(name = "fk_caixa")
    private int idCaixa;

    @Column(name = "fk_produto")
    private Integer idProduto;

}
