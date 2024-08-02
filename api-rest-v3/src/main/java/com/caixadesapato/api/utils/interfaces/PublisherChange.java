package com.caixadesapato.api.utils.interfaces;

import com.caixadesapato.api.model.Doador;

public interface PublisherChange {
    void notifyChange(Doador entity);
}
