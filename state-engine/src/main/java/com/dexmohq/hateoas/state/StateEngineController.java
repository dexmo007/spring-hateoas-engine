package com.dexmohq.hateoas.state;

import com.dexmohq.hateoas.state.swagger.TransitionName;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public abstract class StateEngineController<E extends StateEntity<I>, I, S> implements RepresentationModelAssembler<E, EntityModel<E>> {

    public abstract StateEngine<E, I, S> getStateEngine();

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<E>> getById(@PathVariable I id) {
        return ResponseEntity.of(getStateEngine().getRepository().findById(id).map(this::toModel));
    }

    @PostMapping("/{id}/{transition}")
    public ResponseEntity<EntityModel<E>> postStateTransition(@PathVariable I id, @PathVariable @TransitionName String transition) {
        return ResponseEntity.of(getStateEngine().getRepository().findById(id)
                .map(e -> getStateEngine().transition(e, transition))
                .map(this::toModel))
                ;
    }

    @SuppressWarnings("unchecked")
    @Override
    public EntityModel<E> toModel(E entity) {
        final EntityModel<E> entityModel = EntityModel.of(entity)
                .add(linkTo(methodOn(getClass()).getById(entity.getId())).withSelfRel());
        for (final StateTransition<E, S> transition : getStateEngine().getTransitions()) {
            if (transition.isValid(entity)) {
                entityModel.add(linkTo(methodOn(getClass()).postStateTransition(entity.getId(), transition.getName())).withRel(transition.getName()));
            }
        }
        return entityModel;
    }
}
