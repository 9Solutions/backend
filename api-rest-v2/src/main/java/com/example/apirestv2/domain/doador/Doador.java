package com.example.apirestv2.domain.doador;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;

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

    // @OneToMany
    // private List<Carrinho> carrinhos;
}
