package com.example.apirestv2.service.interfaces;

import com.example.apirestv2.domain.doador.Doador;

public interface PublisherChange {
    void notifyChange(Doador entity);
}
