package com.dexmohq.hateoas.state;

import java.util.Collection;

public interface StateEngineDefinition<E, I, S> {

    String getEngineName();

    Collection<StateTransition<E, S>> getTransitions();

    StateEngineRepository<E, I> getRepository();

}
