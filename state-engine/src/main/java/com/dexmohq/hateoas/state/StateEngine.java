package com.dexmohq.hateoas.state;

import java.util.Collection;

public interface StateEngine<E, I, S> {

    StateEngineRepository<E, I> getRepository();

    E transition(E entity, String name);

    E transitionTo(E entity, S state);

    Collection<StateTransition<E, S>> getTransitions();

}
