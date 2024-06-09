package com.example.apirestv2.service.rotina;

import com.example.apirestv2.domain.caixa.Caixa;
import lombok.Data;

import java.util.List;

@Data
public class NovaCaixa {

    private Caixa caixas;
    private int[] itens;
    private int idsPedidos;

}
