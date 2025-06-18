package com.hps.taskservice.repositories;

import com.hps.taskservice.entities.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskStatusRepository extends JpaRepository<TaskStatus, Long> {

}
