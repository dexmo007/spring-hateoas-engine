package com.dexmohq.hateoas.swagger;

public interface StateEngine<E, S> {

    E transition(E entity, String name);

    E transitionTo(E entity, S state);

}
