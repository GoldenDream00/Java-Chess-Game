package com.project.sms.repository;

import com.project.sms.model.Login;
import com.project.sms.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface LoginRepository extends MongoRepository<Login, String> {
    Login findByEmail(String email);
    List<Login> findByRolesContains(Set<Role> role);
}