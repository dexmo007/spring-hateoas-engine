package com.dexmohq.hateoas.swagger;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class TaskDto {
    @NotEmpty
    private String name;
}
