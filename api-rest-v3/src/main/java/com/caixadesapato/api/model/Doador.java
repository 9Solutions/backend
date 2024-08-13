package com.caixadesapato.api.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_doador")
    private Long id;

    private String nomeCompleto;

    private String identificador;

    private String email;

    @Column(name = "dt_cadastro")
    @CreationTimestamp
    private LocalDate dataCadastro;

    private String telefone;

    private String senha;
    
    private String permissao;

}
