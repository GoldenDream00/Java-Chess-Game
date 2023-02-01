package com.project.sms.repository;

import com.project.sms.model.Guardian;
import com.project.sms.model.Login;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuardianRepository extends MongoRepository<Guardian, String> {
    //Student findByEmail(String email);
    Guardian findByLogin(Login login);
}
