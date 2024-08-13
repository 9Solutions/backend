package com.caixadesapato.api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "dash_user", schema = "db_9solutions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dash_user")
    private Long id;

    private String email;

    private String senha;

}
