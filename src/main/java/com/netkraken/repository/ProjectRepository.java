package com.netkraken.repository;

import com.netkraken.model.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long>
{
    Project findByTaskId(Long id);
    Optional<Project> findById(Long id);
}
