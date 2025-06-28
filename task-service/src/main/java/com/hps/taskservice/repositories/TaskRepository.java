package com.hps.taskservice.repositories;

import com.hps.taskservice.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;


public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByProjectId(Long projectId);
    List<Task> findByOwnerIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            Long ownerId, Date endDate, Date startDate);
    List<Task> findByOwnerId(Long ownerId);

}
