package com.dexmohq.hateoas.swagger;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
public class FooStateEngineDefinition implements StateEngineDefinition<Foo, String> {
    @Override
    public String getEngineName() {
        return "fooStateEngine";
    }

    @Override
    public String getState(Foo entity) {
        return "bar";
    }

    @Override
    public Collection<StateTransition<Foo, String>> getTransitions() {
        return Collections.emptyList();
    }
}
