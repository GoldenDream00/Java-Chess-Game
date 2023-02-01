package com.project.sms.service;

import com.project.sms.model.Event;
import com.project.sms.model.Register;
import com.project.sms.model.Student;
import com.project.sms.repository.EventRepository;
import com.project.sms.repository.RegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegisterService {
    @Autowired
    StudentService studentService;
    @Autowired
    EventService eventService;
    @Autowired
    RegisterRepository registerRepository;
    /*
    * DESC: add Register
    * Param studentId, eventID
    * */
    public boolean addRegister(String studentId, String eventID) {
        //Student student = studentService.findByNickname(studentEmail);
        Student student = studentService.findById(studentId);
        Event event = eventService.findById(eventID);
        Register register = new Register();
        register.setEvent(event);
        register.setStudent(student);
        registerRepository.save(register);
        return true;
    }
    /*
    * DESC cancel the register
    * Param studentId, eventID
    * */
    public boolean cancelRegister(String studentId, String eventID) {
        Student student = studentService.findById(studentId);
        Event event = eventService.findById(eventID);
        registerRepository.deleteByStudentAndEvent(student, event);
        return  true;
    }
    /*
     * DESC check if student is registered to the event.
     * Param studentId, eventID
     * */
    public boolean verifyRegistered(String studentId, String eventId) {
        Student student =studentService.findById(studentId);
        Event event = eventService.findById(eventId);
        Register register = registerRepository.findByStudentAndEvent(student, event);
        if(register == null) return true;
        return false;
    }
    /*
     * DESC get list of registered student given by an event.
     * Param event ID
     * return List of register.
     * */
    public List<Register> findAllStudentsById(String id) {
        Event event = eventService.findById(id);
        List<Register> events = registerRepository.findAllByEvent(event);
        return  events;
    }
}
