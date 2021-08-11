package com.netkraken.repository;

import com.netkraken.model.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long>
{
    Iterable<Task> findAllByUserId(Long id);
}
