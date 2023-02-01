package com.project.sms.controller;

import com.project.sms.model.Event;
import com.project.sms.model.Guardian;
import com.project.sms.model.Register;
import com.project.sms.model.Student;
import com.project.sms.service.EventService;
import com.project.sms.service.GuardianService;
import com.project.sms.service.RegisterService;
import com.project.sms.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/events/")
public class EventController {
    @Autowired
    EventService eventService;
    @Autowired
    RegisterService registerService;
    @Autowired
    StudentService studentService;
    @Autowired
    GuardianService guardianService;
    @RequestMapping("/")
    public String getAll(Model model) {
        List<Event> events = eventService.findAll();
        model.addAttribute("events", events);
        model.addAttribute("registerService", registerService);
        return "events/list";
    }
    @RequestMapping(value = "/create")
    public String createEvent(Model model) {
        Event event = new Event();
        model.addAttribute("event", event);
        return "events/create";
    }
    @PostMapping("/store")
    public String storeEvent(Event event) {
        eventService.createEvent(event);
        return "redirect:/events/";
    }
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable(value = "id") String id) {
        eventService.deleteEvent(id);
        return "redirect:/events/";
    }
    @GetMapping("/update/{id}")
    public String updateUserView(@PathVariable(value = "id") String id, Model model) {
        Event event = eventService.findById(id);
        model.addAttribute("event", event);
        return "events/update";
    }
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable(value = "id") String id,Event event) {
        eventService.updateEvent(id, event);
        return "redirect:/events/";
    }


    /*
     * Desc: when student click details for admin.
     * param: event id
     * return: shows the choose student form. only for admin
     */
    @GetMapping("/register/adminshow/{id}")
    public String registerForm(@PathVariable(value="id") String id, Model model) {
        List<Register> registers = registerService.findAllStudentsById(id);
        model.addAttribute("registers", registers);
        model.addAttribute("id", id);
        return "register/adminRegister";
    }

    /*
     * Desc: when student click details for guardian.
     * param: event id
     * return: shows the choose student form. only for guardian
     */

    @GetMapping("/register/guardianshow/{id}")
    public String registerGuardianForm(@PathVariable(value="id") String id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Guardian guardian = guardianService.findByEmail(authentication.getName());
        List<Student> students = studentService.findAllByGuardian(guardian);
        model.addAttribute("students", students);
        model.addAttribute("id", id);
        model.addAttribute("registerService", registerService);
        return "register/guardianForm";
    }
    /*
    * Desc: when student click register to events
    * param: event id, student id
    * return: redirect to event detail page.
     */
    @GetMapping("/register/events/{id}/{studentId}")
    public String registerEvent(@PathVariable(value="id") String id, @PathVariable(value = "studentId") String  studentId, HttpServletRequest request) {
        registerService.addRegister(studentId,id);
        String referer = request.getHeader("Referer"); // we do this because we have to go to previous url.
        return "redirect:" + referer;
    }
    /*
     * Desc: when student click cancel.
     * param: event id, student id
     * return: redirect to event detail page
     */
    @GetMapping("/unregister/events/{id}/{studentId}")
    public String cancelregisterEvent(@PathVariable(value="id") String id, @PathVariable(value = "studentId") String  studentId, HttpServletRequest request) {
        registerService.cancelRegister(studentId, id);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }


}
