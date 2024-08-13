package com.caixadesapato.api.model.view;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "vw_caixas_em_montagem", schema = "db_9solutions")
public class VwCaixasEmMontagem {

    @Id
    @Column(name = "quantidade_caixas_em_montagem")
    private Integer qtdCaixasEmMontagem;

}
