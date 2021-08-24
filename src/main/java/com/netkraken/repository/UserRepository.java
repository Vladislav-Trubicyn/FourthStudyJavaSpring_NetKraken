package com.netkraken.repository;

import com.netkraken.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    User findByUsername(String username);
    Iterable<User> findBySpecializationAndStatus(String specialization, boolean status);
    Iterable<User> findAllByProject(Long project);
    Iterable<User> findAllByStatus(boolean isStatus);
}
