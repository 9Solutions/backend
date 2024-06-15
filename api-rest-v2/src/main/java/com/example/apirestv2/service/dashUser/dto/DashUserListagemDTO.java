package com.example.apirestv2.service.dashUser.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashUserListagemDTO {
    private Long id;
    private String email;
    private String senha;
}
