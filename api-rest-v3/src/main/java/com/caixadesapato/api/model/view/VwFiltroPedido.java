package com.caixadesapato.api.model.view;

import com.caixadesapato.api.model.Doador;
import com.caixadesapato.api.model.StatusPedido;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "filtros_pedidos")
public class VwFiltroPedido {

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
