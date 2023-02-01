package com.project.sms.repository;

import com.project.sms.model.Event;
import com.project.sms.model.Register;
import com.project.sms.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RegisterRepository extends MongoRepository<Register, String> {
    Register findByStudentAndEvent(Student student, Event event);
    void deleteByStudentAndEvent(Student student, Event event);
    List<Register> findAllByEvent(Event event);
}
