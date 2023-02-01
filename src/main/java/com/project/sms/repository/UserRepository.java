package com.project.sms.repository;

import com.project.sms.model.Login;
import com.project.sms.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByLogin(Login login);
}
