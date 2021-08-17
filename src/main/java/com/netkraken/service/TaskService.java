package com.netkraken.service;

import com.netkraken.model.Task;
import com.netkraken.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService
{
    @Autowired
    private TaskRepository taskRepository;

    public Iterable<Task> findAllTasks()
    {
        return taskRepository.findAll();
    }

    public void saveTask(Task task)
    {
        taskRepository.save(task);
    }

    public Iterable<Task> findAllByUserId(Long id)
    {
        return taskRepository.findAllByUserId(id);
    }

    public Task findById(long id)
    {
        return taskRepository.findById(id);
    }

    public Iterable<Task> findAllByProject(boolean isProject)
    {
        return taskRepository.findAllByProject(isProject);
    }
}
