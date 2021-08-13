package com.netkraken.service;

import com.netkraken.model.Project;
import com.netkraken.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectService
{
    @Autowired
    private ProjectRepository projectRepository;

    public Iterable<Project> findAllProjects()
    {
        return projectRepository.findAll();
    }

    public void saveProject(Project project)
    {
        projectRepository.save(project);
    }

    public Project findByTaskId(Long id)
    {
        return projectRepository.findByTaskId(id);
    }

    public Optional<Project> findById(Long id)
    {
        return projectRepository.findById(id);
    }
}
