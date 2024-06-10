package com.example.apirestv2.domain.vwCaixasEmMontagem;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "vw_caixas_em_montagem", schema = "db_9solutions")
public class VwCaixasEmMontagem {
    @Id
    @Column(name = "quantidade_caixas_em_montagem")
    private Integer qtdCaixasEmMontagem;
}
