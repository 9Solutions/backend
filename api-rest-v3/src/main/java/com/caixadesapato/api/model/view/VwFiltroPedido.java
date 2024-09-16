package com.caixadesapato.api.model.view;

import com.caixadesapato.api.model.Caixa;
import com.caixadesapato.api.model.Doador;
import com.caixadesapato.api.model.StatusCaixa;
import com.caixadesapato.api.model.StatusPedido;
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
@Table(name = "vw_filtros_pedidos")
public class VwFiltroPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpedido")
    private Integer id;

    @Column(name = "valor_total")
    private Double valorTotal;

    @Column(name = "data_pedido")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dataPedido;

    @Column(name = "qtd_caixas")
    private int quantidadeCaixas;

    @ManyToOne
    @JoinColumn(name = "fk_status", insertable = true)
    private StatusCaixa statusCaixa;

    @ManyToOne
    @JoinColumn(name = "fk_status_pedido", insertable = true)
    private StatusPedido statusPedido;

    @ManyToOne
    @JoinColumn(name = "fk_doador", insertable = true)
    private Doador doador;

}
