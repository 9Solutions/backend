package com.caixadesapato.api.model;

import com.caixadesapato.api.utils.enums.Genero;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "caixa")
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
    private Genero genero;

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

    @ManyToOne
    @JoinColumn(name = "fk_faixa_etaria", insertable = true)
    private FaixaEtaria faixaEtaria;

    @ManyToOne
    @JoinColumn(name = "fk_pedido", insertable = true)
    private Pedido pedido;

    @OneToMany(mappedBy = "caixa", fetch = FetchType.LAZY)
    private List<ItemCaixa> itens;

    @OneToMany(mappedBy = "caixa", fetch = FetchType.LAZY)
    private List<EtapaCaixa> etapas;

}
