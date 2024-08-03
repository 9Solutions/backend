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
    private Integer idPedido;

    @Column(name = "data_pedido")
    private String dataPedido;

    @Column(name = "valor_total")
    private Integer valorPedido;

    @Column(name = "fk_doador")
    private Integer fkDoador;

    @Column(name = "id_status_pedido")
    private Integer idStatusPedido;

    @Column(name = "id_doador")
    private Integer idDoador;

    @Column(name = "nome_completo")
    private String nomeDoador;

    @Column(name = "telefone")
    private String telefone;

    @Column(name="status")
    private String status;

}
