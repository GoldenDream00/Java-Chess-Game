package com.project.sms.controller;

import com.project.sms.model.Guardian;
import com.project.sms.model.Teacher;
import com.project.sms.service.GuardianService;
import com.project.sms.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/teachers/")
public class TeacherController {
    @Autowired
    TeacherService teacherService;
    @RequestMapping("/")
    public String getAll(Model model) {
        List<Teacher> teachers = teacherService.findAll();
        model.addAttribute("teachers", teachers);
        return "teachers/list";
    }
    @RequestMapping(value = "/create")
    public String createTeacher(Model model) {
        Teacher teacher = new Teacher();
        model.addAttribute("teacher", teacher);
        return "teachers/create";
    }
    @PostMapping("/store")
    public String storeTeacher(Teacher teacher) {
        teacherService.createTeacher(teacher);
        return "redirect:/teachers/";
    }
    @GetMapping("/delete/{id}")
    public String deleteTeacher(@PathVariable(value = "id") String id) {
        teacherService.deleteTeacher(id);
        return "redirect:/teachers/";
    }
    @GetMapping("/update/{id}")
    public String updateGuardianView(@PathVariable(value = "id") String id, Model model) {
        Teacher teacher = teacherService.findById(id);
        teacher.getLogin().setPassword("");
        model.addAttribute("teacher", teacher);
        return "teachers/update";
    }
    @PostMapping("/update/{id}")
    public String updateGuardian(@PathVariable(value = "id") String id, Teacher teacher) {
        teacherService.updateTeacher(id, teacher);
        return "redirect:/teachers/";
    }
}
