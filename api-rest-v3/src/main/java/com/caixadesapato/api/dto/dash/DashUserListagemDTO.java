package com.caixadesapato.api.dto.dash;

import lombok.*;

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
