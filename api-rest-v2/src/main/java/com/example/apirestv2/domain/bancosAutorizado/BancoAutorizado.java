package com.example.apirestv2.domain.bancosAutorizado;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BancoAutorizado {
    private String ispb;
    private String name;
    private int code;
    private String fullName;
}
