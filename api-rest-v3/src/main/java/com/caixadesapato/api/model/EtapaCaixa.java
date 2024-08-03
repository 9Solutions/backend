package com.caixadesapato.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "etapa_caixa", schema = "db_9solutions")
public class EtapaCaixa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_etapa_caixa")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "fk_status")
    private StatusCaixa status;

    @ManyToOne
    @JoinColumn(name = "fk_id_caixa")
    private Caixa caixa;

    @CreationTimestamp
    @Column(name = "update_at")
    private LocalDateTime updateAt;


}
