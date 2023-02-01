package com.project.sms.repository;

import com.project.sms.model.Guardian;
import com.project.sms.model.Login;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.project.sms.model.Student;
import org.springframework.stereotype.Repository;

import javax.management.relation.Role;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends MongoRepository<Student, String>{
    //Student findByEmail(String email);
    List<Student> findByGuardianContains(List<Guardian> guardians);
    Student findByNickname(String nickname);
}