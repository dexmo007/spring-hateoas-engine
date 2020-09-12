package com.dexmohq.hateoas.state;

import org.springframework.context.annotation.Bean;

public abstract class StateEngineConfiguration<E extends StateEntity<I>, I, S> implements StateEngineDefinition<E, I, S> {



    @Bean
    StateEngine<E, I, S> stateEngine() {
        return new StateEngineImpl<>(this);
    }

}
