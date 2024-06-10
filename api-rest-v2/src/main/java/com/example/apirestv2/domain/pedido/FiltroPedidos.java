package com.example.apirestv2.domain.pedido;

import com.example.apirestv2.domain.caixa.Caixa;
import com.example.apirestv2.domain.doador.Doador;
import com.example.apirestv2.domain.statusPedido.StatusPedido;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "filtros_pedidos")
public class FiltroPedidos {

    @Id
    @Column(name = "idpedido")
    private String idPedido;

    @Column(name = "data_pedido")
    private String dataPedido;

    @Column(name = "valor_total")
    private String valorPedido;

    @ManyToOne
    @JoinColumn(name = "id_status_pedido", insertable = true)
    private StatusPedido statusPedido;

    @ManyToOne
    @JoinColumn(name = "fk_doador", insertable = true)
    private Doador doador;

}
