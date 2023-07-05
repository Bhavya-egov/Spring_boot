package com.example.AssignmentSpringBootApplication.repository_inter;

import com.example.AssignmentSpringBootApplication.User;

import java.util.UUID;

public interface Repo {
    int create(User user);
    int update(User user);
    User findById(UUID id);
    User find(UUID id, String phone_no);
    int deleteByID(UUID id);
    User findByBool(boolean active);

}