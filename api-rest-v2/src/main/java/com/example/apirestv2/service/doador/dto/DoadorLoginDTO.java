package com.example.apirestv2.service.doador.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoadorLoginDTO {
    private String email;
    private String senha;
}
