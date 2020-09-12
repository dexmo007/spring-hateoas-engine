package com.dexmohq.hateoas.state;

public interface StateTransition<E, S> {

    String getName();

    S getTarget();

    boolean isValid(E entity);

    E perform(E entity);
}
