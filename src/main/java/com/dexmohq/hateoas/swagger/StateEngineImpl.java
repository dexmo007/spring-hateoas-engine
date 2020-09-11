package com.dexmohq.hateoas.swagger;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.BeanFactory;

@RequiredArgsConstructor
public class StateEngineImpl<E, S> implements StateEngine<E, S> {

    private final StateEngineDefinition<E, S> definition;
    private final BeanFactory beanFactory;

    @Override
    public E transition(E entity, String name) {
        final StateTransition<E, S> transition = definition.getTransitions().stream()
                .filter(t -> t.getName().equals(name))
                .findAny()
                .orElseThrow(() -> new UnknownTransitionException(name));
        if (!transition.isValid(entity)) {
            throw new InvalidTransitionException();
        }
        final E transitionedEntity = transition.perform(entity);
        final StateEnginePersistence<E> persistence = beanFactory.getBean(definition.persistence());
        return persistence.persist(transitionedEntity);
    }

    @Override
    public E transitionTo(E entity, S state) {
        throw new UnsupportedOperationException();
    }
}
