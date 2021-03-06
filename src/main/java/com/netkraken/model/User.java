package com.netkraken.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private boolean action;
    private boolean status;
    private String specialization;
    private Long project;

    @Transient
    private Long selectedId;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public boolean isAction()
    {
        return action;
    }

    public void setAction(boolean action)
    {
        this.action = action;
    }

    public Set<Role> getRoles()
    {
        return roles;
    }

    public void setRoles(Set<Role> roles)
    {
        this.roles = roles;
    }

    public boolean isStatus()
    {
        return status;
    }

    public void setStatus(boolean status)
    {
        this.status = status;
    }

    public String getSpecialization()
    {
        return specialization;
    }

    public void setSpecialization(String specialization)
    {
        this.specialization = specialization;
    }

    public Long getProject()
    {
        return project;
    }

    public void setProject(Long project)
    {
        this.project = project;
    }

    public Long getSelectedId()
    {
        return selectedId;
    }

    public void setSelectedId(Long selectedId)
    {
        this.selectedId = selectedId;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return isAction();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return getRoles();
    }
}
