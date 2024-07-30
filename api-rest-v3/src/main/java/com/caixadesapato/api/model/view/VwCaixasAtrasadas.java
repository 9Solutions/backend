package com.caixadesapato.api.model.view;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class VwCaixasAtrasadas {

    @Id
    @Column(name = "quantidade_caixas_atrasadas")
    private Integer qtdCaixasAtrasadas;

}
