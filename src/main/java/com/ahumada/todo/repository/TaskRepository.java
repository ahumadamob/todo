package com.ahumada.todo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ahumada.todo.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByDueDateNotNullOrderByDueDate();    
    List<Task> findByDueDateNull();
    List<Task> findByDueDateNotNullAndDoneTrueOrderByDueDate();
    List<Task> findByDueDateNullAndDoneTrue();
    List<Task> findByDueDateNotNullAndDoneFalseOrderByDueDate();
    List<Task> findByDueDateNullAndDoneFalse();    
    
}
