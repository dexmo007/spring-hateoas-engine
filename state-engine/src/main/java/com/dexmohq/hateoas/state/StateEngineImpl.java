package com.dexmohq.hateoas.state;

import com.dexmohq.hateoas.state.exception.InvalidTransitionException;
import com.dexmohq.hateoas.state.exception.UnknownTransitionException;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@RequiredArgsConstructor
public class StateEngineImpl<E, I, S> implements StateEngine<E, I, S> {

    private final StateEngineDefinition<E, I, S> definition;

    private StateEngineRepository<E, I> stateEngineRepository;

    @Override
    public StateEngineRepository<E, I> getRepository() {
        if (stateEngineRepository == null) {
            stateEngineRepository = definition.getRepository();
        }
        return stateEngineRepository;
    }

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
        return getRepository().save(transitionedEntity);
    }

    @Override
    public E transitionTo(E entity, S state) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<StateTransition<E, S>> getTransitions() {
        return definition.getTransitions();
    }
}
