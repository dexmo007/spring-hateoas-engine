package com.dexmohq.hateoas.swagger;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class Task {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;
    @NotNull
    private String name;
    @NotNull
    @Enumerated(EnumType.STRING)
    private State state;

    public boolean hasState(State state) {
        return this.state == state;
    }

    public enum State {
        CREATED, STARTED, COMPLETED
    }

}
