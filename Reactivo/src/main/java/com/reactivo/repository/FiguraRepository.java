package com.reactivo.repository;

import com.reactivo.model.Figura;
import com.reactivo.model.Modelo;
import reactor.core.publisher.Flux;

public interface FiguraRepository extends Repository<Long, Figura> {

    Flux<Figura> findAllByModelo(Modelo modelo);
}
