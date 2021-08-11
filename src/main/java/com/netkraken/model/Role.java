package com.netkraken.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority
{
    USER, PROGRAMMER, MANAGER, ADMIN;

    @Override
    public String getAuthority()
    {
        return name();
    }
}
