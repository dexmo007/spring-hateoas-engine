package com.dexmohq.hateoas.state.sample.task;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class TaskDto {
    @NotEmpty
    private String name;
}
