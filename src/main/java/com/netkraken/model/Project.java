package com.netkraken.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "projects")
public class Project
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private Long taskId;
    private Long customerId;
    private Long managerId;
    private int countProgrammers;
    private String status;

    @OneToMany
    @JoinTable(name = "project_programmers", joinColumns = {@JoinColumn(name = "project_id")}, inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Set<User> programmers = new HashSet<>();

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Long getTaskId()
    {
        return taskId;
    }

    public void setTaskId(Long taskId)
    {
        this.taskId = taskId;
    }

    public Long getCustomerId()
    {
        return customerId;
    }

    public void setCustomerId(Long customerId)
    {
        this.customerId = customerId;
    }

    public Long getManagerId()
    {
        return managerId;
    }

    public void setManagerId(Long managerId)
    {
        this.managerId = managerId;
    }

    public int getCountProgrammers()
    {
        return countProgrammers;
    }

    public void setCountProgrammers(int countProgrammers)
    {
        this.countProgrammers = countProgrammers;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Set<User> getProgrammers()
    {
        return programmers;
    }

    public void setProgrammers(Set<User> programmersId)
    {
        this.programmers = programmers;
    }
}
