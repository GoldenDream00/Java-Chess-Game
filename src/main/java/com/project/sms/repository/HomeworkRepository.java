package com.project.sms.repository;

import com.project.sms.model.Homework;
import com.project.sms.model.Student;
import com.project.sms.model.Teacher;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface HomeworkRepository extends MongoRepository<Homework, String> {
    List<Homework> findAllByStudent(Student student);
    List<Homework> findAllByTeacher(Teacher teacher);
    List<Homework> findAllByStudentIn(List<Student> students);
}
