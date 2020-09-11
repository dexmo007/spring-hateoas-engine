package com.dexmohq.hateoas.swagger;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class TaskRepresentationModelAssembler implements RepresentationModelAssembler<Task, EntityModel<Task>> {

    @Override
    public EntityModel<Task> toModel(Task entity) {
        final String id = entity.getId();
        final EntityModel<Task> entityModel = EntityModel.of(entity)
                .add(linkTo(methodOn(TaskController.class).getById(id)).withSelfRel());
        switch (entity.getState()) {
            case CREATED:
                entityModel.add(linkTo(methodOn(TaskController.class).startById(id)).withRel("start"));
                entityModel.add(linkTo(methodOn(TaskController.class).completeById(id)).withRel("complete"));
                break;
            case STARTED:
                entityModel.add(linkTo(methodOn(TaskController.class).completeById(id)).withRel("complete"));
                break;
            case COMPLETED:
                break;
            default:
        }
        return entityModel;
    }


}
