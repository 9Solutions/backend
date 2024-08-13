package com.caixadesapato.api.model.view;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "vw_caixas_atrasadas", schema = "db_9solutions")
public class VwCaixasAtrasadas {

    @Id
    @Column(name = "quantidade_caixas_atrasadas")
    private Integer qtdCaixasAtrasadas;

}
