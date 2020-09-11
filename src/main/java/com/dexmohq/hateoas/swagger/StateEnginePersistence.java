package com.dexmohq.hateoas.swagger;

public interface StateEnginePersistence<E> {
    E persist(E entity);
}
