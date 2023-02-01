package com.project.sms.repository;

import com.project.sms.model.Login;
import com.project.sms.model.Teacher;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends MongoRepository<Teacher, String> {
    //Student findByEmail(String email);
    Teacher findByLogin(Login login);
}

