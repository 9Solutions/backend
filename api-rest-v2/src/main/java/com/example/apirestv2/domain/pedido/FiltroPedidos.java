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

    @Column(name = "fk_doador")
    private String fkDoador;

    @Column(name = "id_status_pedido")
    private String idStatusPedido;

    @Column(name = "id_doador")
    private String idDoador;

    @Column(name = "nome_completo")
    private String nomeDoador;

    @Column(name = "email")
    private String email;

    @Column(name = "telefone")
    private String telefone;

    @Column(name="status")
    private String status;

}
