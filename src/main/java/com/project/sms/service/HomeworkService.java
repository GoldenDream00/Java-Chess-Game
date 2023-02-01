package com.project.sms.service;

import com.project.sms.model.*;
import com.project.sms.repository.HomeworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class HomeworkService {
    @Autowired
    HomeworkRepository homeworkRepository;
    @Autowired
    TeacherService teacherService;
    @Autowired
    StudentService studentService;
    @Autowired
    PuzzleService puzzleService;
    @Autowired
    GuardianService guardianService;
    ///Get the list of homeworks each by teacher.
    public List<Homework> findAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // get loggged in user
        Teacher teacher = teacherService.findByEmail(authentication.getName());
        //If admin, then get all of homework info.
        Collection<?extends GrantedAuthority> granted = authentication.getAuthorities();
        if((granted.toArray()[0]+"").matches("ADMIN")) {
            return homeworkRepository.findAll();
        }
        return homeworkRepository.findAllByTeacher(teacher);
    }
    public void createHomework(String studentId, String teacherId, List<String> puzzleID,  Homework homework) {
//        System.out.println(studentId);
        Student student = studentService.findById(studentId);
        List<Puzzle> puzzles= puzzleService.findAllByIDs(puzzleID);
        homework.setStudent(student);
        homework.setPuzzle(puzzles);
        if(teacherId != null) {
            Teacher teacher = teacherService.findById(teacherId);
            homework.setTeacher(teacher);
        }
        else {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // get loggged in user
            Teacher teacher = teacherService.findByEmail(authentication.getName());
            homework.setTeacher(teacher);
        }
        homeworkRepository.save(homework);
    }

    public boolean updateHomework(String id, Homework homework, String studentId, String teacherId, List<String> puzzleId) {
        Homework previousHomework = homeworkRepository.findById(id).get();
        homework.setDataPublished(previousHomework.getDataPublished());

        Student student = studentService.findById(studentId);
        List<Puzzle> puzzles= puzzleService.findAllByIDs(puzzleId);
        if(teacherId!= null) {
            Teacher teacher = teacherService.findById(teacherId);
            homework.setTeacher(teacher);
        }
        else {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // get loggged in user
            Teacher teacher = teacherService.findByEmail(authentication.getName());
            homework.setTeacher(teacher);
        }
        homework.setStudent(student);
        homework.setPuzzle(puzzles);
        homeworkRepository.save(homework);
        return true;
    }
    public void deleteHomework(String id) {
        homeworkRepository.deleteById(id);
    }
    public Homework findById(String id) {
        Optional<Homework> homeworkOptional =  homeworkRepository.findById(id);
        return homeworkOptional.get();
    }
    public List<Homework> findAllByStudentId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // get loggged in user
        Guardian guardian = guardianService.findByEmail(authentication.getName());
        List<Student> students = studentService.findAllByGuardian(guardian);
        List<Homework> homeworks = homeworkRepository.findAllByStudentIn(students);
        return homeworks;
    }
}
