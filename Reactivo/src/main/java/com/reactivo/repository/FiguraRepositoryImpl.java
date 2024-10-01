package com.reactivo.repository;

import com.reactivo.model.Figura;
import com.reactivo.model.Modelo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FiguraRepositoryImpl implements FiguraRepository {


    @Override
    public Flux<Figura> findAllByModelo(Modelo modelo) {
        return null;
    }

    @Override
    public Flux<Figura> findAll() {
        return null;
    }

    @Override
    public Mono<Figura> findById(Long aLong) {
        return null;
    }

    @Override
    public Mono<Figura> save(Figura figura) {
        return null;
    }

    @Override
    public Mono<Figura> update(Long aLong, Figura figura) {
        return null;
    }

    @Override
    public Mono<Void> delete(Long aLong) {
        return null;
    }
}
