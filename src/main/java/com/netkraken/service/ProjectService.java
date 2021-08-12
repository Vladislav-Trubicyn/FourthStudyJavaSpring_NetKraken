package com.netkraken.service;

import com.netkraken.model.Project;
import com.netkraken.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService
{
    @Autowired
    private ProjectRepository projectRepository;

    public Iterable<Project> findAllProjects()
    {
        return projectRepository.findAll();
    }
}
