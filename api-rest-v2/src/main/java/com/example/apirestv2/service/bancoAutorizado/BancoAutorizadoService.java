package com.example.apirestv2.service.bancoAutorizado;

import com.example.apirestv2.service.bancoAutorizado.dto.BancoAutorizadoExternoDTO;
import com.example.apirestv2.service.bancoAutorizado.dto.BancoAutorizadoListagemDTO;
import com.example.apirestv2.utils.ListaObj;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class BancoAutorizadoService {
    public List<BancoAutorizadoExternoDTO> fetchAll(){
        RestClient client = RestClient.builder()
                .baseUrl("https://brasilapi.com.br/api/")
                .messageConverters(httpMessageConverters -> httpMessageConverters.add(new MappingJackson2HttpMessageConverter()))
                .build();

        String raw = client.get()
                .uri("/banks/v1")
                .retrieve()
                .body(String.class);


        List<BancoAutorizadoExternoDTO> bancos = client.get()
                .uri("/banks/v1")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        return tratamentoDados(bancos);
    }

    private List<BancoAutorizadoExternoDTO> tratamentoDados(List<BancoAutorizadoExternoDTO> dto){
        return dto.stream().filter(banco -> Objects.nonNull(banco.getNomeCompleto())).toList();
    }

    public List<BancoAutorizadoListagemDTO> ordenacaoPorNome(List<BancoAutorizadoListagemDTO> dto){
        List<BancoAutorizadoListagemDTO> listTemp = new ArrayList<>(dto);

        // Insert Sort
        for (int i = 1; i < listTemp.size(); i++) {
            BancoAutorizadoListagemDTO key = listTemp.get(i);
            int j = i - 1;

            while (j >= 0 && listTemp.get(j).getFullName().compareTo(key.getFullName()) > 0) {
                listTemp.set(j + 1, listTemp.get(j));
                j = j - 1;
            }
            listTemp.set(j + 1, key);
        }

        return listTemp;
    }
}
