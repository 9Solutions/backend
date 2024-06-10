package com.example.apirestv2.domain.vwCaixasParaEntregar;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "vw_caixas_para_entregar", schema = "db_9solutions")
public class VwCaixasParaEntregar {
    @Id
    @Column(name = "quantidade_caixas_para_entregar")
    private Integer qtdCaixasParaEntregar;
}
