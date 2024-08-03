package com.caixadesapato.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "status_caixa", schema = "db_9solutions")
@Getter
@Setter
public class StatusCaixa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Status_caixa")
    private Integer id;

    @Column(name = "status")
    private String status;

}
