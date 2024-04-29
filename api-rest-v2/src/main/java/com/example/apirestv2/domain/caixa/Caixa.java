package com.example.apirestv2.domain.caixa;

import com.example.apirestv2.domain.itemCaixa.ItemCaixa;
import com.example.apirestv2.service.produto.enums.EnumGenero;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "caixa", schema = "db_9solutions")
public class Caixa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_caixa")
    private Integer id;

    @Column(name = "genero")
    @Enumerated(EnumType.STRING)
    private EnumGenero genero;

    @Column(name = "carta")
    private String carta;

    @Column(name = "url")
    private String url;

    @Column(name = "qtd")
    private Integer quantidade;

    @Column(name = "dt_criacao")
    @CreationTimestamp
    private LocalDate dataCriacao;

    @Column(name = "dt_entrega")
    private LocalDate dataEntrega;

    @Column(name = "fk_faixa_etaria")
    private int faixaEtaria;

    @Column(name = "fk_pedido")
    private int idPedido;

    @OneToMany
    @JoinColumn(name = "fk_caixa", insertable = false, updatable = false)
    private List<ItemCaixa> itens;

}
