package com.dexmohq.hateoas.swagger;

import com.dexmohq.hateoas.swagger.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskRepository taskRepository;
    private final TaskRepresentationModelAssembler assembler;

    @PostMapping
    public ResponseEntity<EntityModel<Task>> create(@Valid @RequestBody TaskDto dto) {
        final Task task = new Task();
        task.setName(dto.getName());
        task.setState(Task.State.CREATED);
        taskRepository.save(task);
        final EntityModel<Task> entityModel = assembler.toModel(task);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    private Task forceGet(String id) {
        return taskRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    @GetMapping("/{id}")
    public EntityModel<Task> getById(@PathVariable String id) {
        final Task task = forceGet(id);
        return assembler.toModel(task);
    }

    @PostMapping("/{id}/start")
    public EntityModel<Task> startById(@PathVariable String id) {
        final Task task = forceGet(id);
        if (task.getState() != Task.State.CREATED) {
            throw new IllegalTaskStateException(Task.State.STARTED, List.of(Task.State.CREATED));
        }
        task.setState(Task.State.STARTED);
        taskRepository.save(task);
        return assembler.toModel(task);
    }

    @PostMapping("/{id}/complete")
    public EntityModel<Task> completeById(@PathVariable String id) {
        final Task task = forceGet(id);
        if (task.hasState(Task.State.COMPLETED)) {
            throw new IllegalTaskStateException(Task.State.COMPLETED, List.of(Task.State.CREATED, Task.State.STARTED));
        }
        task.setState(Task.State.COMPLETED);
        taskRepository.save(task);
        return assembler.toModel(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        taskRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(IllegalTaskStateException.class)
    public ResponseEntity<Problem> handleIllegalTaskStateException(IllegalTaskStateException e) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle("illegal state")
                        .withDetail(e.getMessage())
                        .withProperties(Map.of(
                                "target", e.getTarget(),
                                "allowedFrom", e.getAllowed()
                        )));
    }

}
