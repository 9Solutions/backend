package com.example.apirestv2.domain.categoria;

import com.example.apirestv2.domain.produto.Produto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "categoria_produto", schema = "db_9solutions")
@Getter
@Setter
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria_produto")
    private Integer id;
    @Column(name = "nome")
    private String nome;

    @OneToMany(mappedBy = "categoriaProduto")
    private List<Produto> produtos;
}
