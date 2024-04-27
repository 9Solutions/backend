package com.example.apirestv2.domain.pedido;

import com.example.apirestv2.domain.caixa.Caixa;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.util.List;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "pedido", schema = "db_9solutions")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpedido")
    private Integer id;

    @Column(name = "valor_total")
    private Double valorTotal;

    @Column(name = "data_pedido")
    @CreationTimestamp
    private LocalDateTime dataPedido;

    @Column(name = "fk_status_pedido")
    private int statusPedido;

    @Column(name = "fk_doador")
    private int idDoador;

    @OneToMany(mappedBy = "idPedido",fetch = FetchType.LAZY)
    private List<Caixa> caixas;

}
