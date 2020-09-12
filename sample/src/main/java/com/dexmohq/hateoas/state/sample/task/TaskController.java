package com.dexmohq.hateoas.state.sample.task;

import com.dexmohq.hateoas.state.StateEngine;
import com.dexmohq.hateoas.state.StateEngineController;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController extends StateEngineController<Task, String, Task.State> {

    private final TaskRepository taskRepository;
    @Getter
    private final StateEngine<Task, String, Task.State> stateEngine;

    @PostMapping
    public ResponseEntity<EntityModel<Task>> create(@Valid @RequestBody TaskDto dto) {
        final Task task = new Task();
        task.setName(dto.getName());
        task.setState(Task.State.CREATED);
        taskRepository.save(task);
        final EntityModel<Task> entityModel = this.toModel(task);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
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
