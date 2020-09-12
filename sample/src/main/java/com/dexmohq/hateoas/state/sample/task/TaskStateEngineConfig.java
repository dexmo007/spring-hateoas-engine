package com.dexmohq.hateoas.state.sample.task;

import com.dexmohq.hateoas.state.StateEngineConfiguration;
import com.dexmohq.hateoas.state.StateEngineRepository;
import com.dexmohq.hateoas.state.StateTransition;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
@RequiredArgsConstructor
public class TaskStateEngineConfig extends StateEngineConfiguration<Task, String, Task.State> {


    private final TaskRepository taskRepository;

    @Override
    public String getEngineName() {
        return "taskStateEngine";
    }

    @Override
    public Collection<StateTransition<Task, Task.State>> getTransitions() {
        return List.of(
                new Transition("start", Task.State.STARTED, Task.State.CREATED),
                new Transition("complete", Task.State.COMPLETED, Task.State.CREATED, Task.State.STARTED)
        );
    }


    public static class Transition implements StateTransition<Task, Task.State> {
        @Getter
        private final String name;
        @Getter
        private final Task.State target;

        private final Set<Task.State> allowed;

        public Transition(String name, Task.State target, Task.State firstAllowed, Task.State... restAllowed) {
            this.name = name;
            this.target = target;
            this.allowed = new HashSet<>();
            allowed.add(firstAllowed);
            if (restAllowed != null) {
                allowed.addAll(Arrays.asList(restAllowed));
            }
        }

        @Override
        public boolean isValid(Task entity) {
            return allowed.contains(entity.getState());
        }

        @Override
        public Task perform(Task entity) {
            entity.setState(target);
            return entity;
        }
    }

    @Override
    public StateEngineRepository<Task, String> getRepository() {
        return taskRepository;
    }
}
