package com.caixadesapato.api.model.view;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "vw_caixas_para_entregar", schema = "db_9solutions")
public class VwCaixasParaEntregar {

    @Id
    @Column(name = "quantidade_caixas_para_entregar")
    private Integer qtdCaixasParaEntregar;

}
