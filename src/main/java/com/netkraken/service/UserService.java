package com.netkraken.service;

import com.netkraken.model.User;
import com.netkraken.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService
{
    @Autowired
    private UserRepository userRepository;

    public Iterable<User> findAllUsers()
    {
        return userRepository.findAll();
    }

    public Iterable<User> findAllByStatus(boolean isStatus)
    {
        return userRepository.findAllByStatus(isStatus);
    }

    public User findByUsername(String username)
    {
        return userRepository.findByUsername(username);
    }

    public void saveUser(User user)
    {
        userRepository.save(user);
    }

    public Iterable<User> findBySpecializationAndStatus(String specialization, boolean status)
    {
        return userRepository.findBySpecializationAndStatus(specialization, status);
    }

    public Iterable<User> findAllByProject(Long project)
    {
        return userRepository.findAllByProject(project);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        return userRepository.findByUsername(username);
    }
}
