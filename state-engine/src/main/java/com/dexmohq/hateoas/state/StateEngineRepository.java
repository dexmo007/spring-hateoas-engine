package com.dexmohq.hateoas.state;

import java.util.Optional;

public interface StateEngineRepository<E, I> {
    E save(E entity);

    Optional<E> findById(I id);
}
