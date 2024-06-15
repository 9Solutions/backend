package com.example.apirestv2.domain.vwCaixasAtrasadas;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "vw_caixas_atrasadas", schema = "db_9solutions")
public class VwCaixasAtrasadas {
    @Id
    @Column(name = "quantidade_caixas_atrasadas")
    private Integer qtdCaixasAtrasadas;
}