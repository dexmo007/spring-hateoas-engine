package com.dexmohq.hateoas.swagger;

public interface StateTransition<E, S> {

    String getName();

    S getTarget();

    boolean isValid(E entity);

    E perform(E entity);
}
