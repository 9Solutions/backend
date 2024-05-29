package com.example.apirestv2.domain.vwCaixasEmMontagem;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "vw_caixas_em_montagem", schema = "db_9solutions")
public class VwCaixasEmMontagem {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer id;
    private Integer qtdCaixasEmMontagem;
}
