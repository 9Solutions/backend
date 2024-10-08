package com.example.apirestv2.domain.pedido;

import com.example.apirestv2.domain.caixa.Caixa;
import com.example.apirestv2.domain.doador.Doador;
import com.example.apirestv2.domain.statusPedido.StatusPedido;
import com.example.apirestv2.service.statusPedido.dto.StatusPedidoListagem;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;

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
    private LocalDate dataPedido;

    @ManyToOne
    @JoinColumn(name = "fk_status_pedido", insertable = true)
    private StatusPedido statusPedido;

    @ManyToOne
    @JoinColumn(name = "fk_doador", insertable = true)
    private Doador doador;

    @OneToMany(mappedBy = "pedido")
    private List<Caixa> caixas;
}
