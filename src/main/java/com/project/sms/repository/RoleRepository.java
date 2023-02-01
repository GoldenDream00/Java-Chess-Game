package com.project.sms.repository;

import com.project.sms.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {
    Role findByRole(String role);

}
