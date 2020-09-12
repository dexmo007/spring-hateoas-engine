package com.dexmohq.hateoas.state.sample.task;

import com.dexmohq.hateoas.state.StateEngineRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, String>, StateEngineRepository<Task, String> {
}
