package com.project.sms.service;

import com.project.sms.model.Guardian;
import com.project.sms.model.Login;
import com.project.sms.model.Teacher;
import com.project.sms.repository.GuardianRepository;
import com.project.sms.repository.LoginRepository;
import com.project.sms.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    AuthService authService;
    @Autowired
    AddressService addressService;
    @Autowired
    LoginRepository loginRepository;

    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }
    public void createTeacher(Teacher teacher) {
        authService.saveLogin(teacher.getLogin(), "TEACHER");
        addressService.saveAddress(teacher.getAddress());
        teacherRepository.save(teacher);
    }
    public boolean updateTeacher(String id, Teacher teacher) {
        Teacher teacher1 = teacherRepository.findById(id).get();
        Login login = authService.findByEmail(teacher.getLogin().getEmail());
        if(login != null && !teacher1.getLogin().getEmail().matches(login.getEmail())) {
            return false;
        }
        teacher.getLogin().setId(teacher1.getLogin().getId());
        teacher.getLogin().setRoles(teacher1.getLogin().getRoles());
        teacher.getAddress().setId(teacher1.getAddress().getId());
        authService.saveLogin(teacher.getLogin(), "TEACHER");
        addressService.saveAddress(teacher.getAddress());
        teacherRepository.save(teacher);
        return true;
    }
    public void deleteTeacher(String id) {
        Teacher teacher = teacherRepository.findById(id).get();
        authService.deleteById(teacher.getLogin().getId());
        addressService.deleteById(teacher.getAddress().getId());
        teacherRepository.deleteById(id);
    }
    public Teacher findById(String id) {
        Optional<Teacher> teacherOptional =  teacherRepository.findById(id);
        return teacherOptional.get();
    }
    public Teacher findByEmail(String email) {
        Login login = loginRepository.findByEmail(email);
        Teacher teacherOptional =  teacherRepository.findByLogin(login);
        return teacherOptional;
    }
}
