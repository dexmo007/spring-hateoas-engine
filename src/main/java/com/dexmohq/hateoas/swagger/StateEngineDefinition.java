package com.dexmohq.hateoas.swagger;

import java.util.Collection;

public interface StateEngineDefinition<E, S> {

    String getEngineName();

    S getState(E entity);

    Collection<StateTransition<E, S>> getTransitions();

    @SuppressWarnings({"rawtypes", "unchecked"})
    default Class<StateEnginePersistence<E>> persistence() {
        return (Class) RepositoryPersistence.class;
    }

}
