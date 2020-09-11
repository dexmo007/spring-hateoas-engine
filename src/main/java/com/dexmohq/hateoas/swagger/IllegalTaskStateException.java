package com.dexmohq.hateoas.swagger;

import lombok.Getter;

import java.util.Collection;
import java.util.List;

@Getter
public class IllegalTaskStateException extends RuntimeException {
    private final Task.State target;
    private final Collection<Task.State> allowed;

    public IllegalTaskStateException(Task.State target, List<Task.State> allowed) {
        super("Can't reach state " + target + ". Only allowed from states: " + allowed);
        this.target = target;
        this.allowed = allowed;
    }
}
