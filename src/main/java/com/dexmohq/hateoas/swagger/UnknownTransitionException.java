package com.dexmohq.hateoas.swagger;

import lombok.Getter;

public class UnknownTransitionException extends RuntimeException {
    @Getter
    private final String name;

    public UnknownTransitionException(String name) {
        super("Unknown transition: " + name);
        this.name = name;
    }
}
