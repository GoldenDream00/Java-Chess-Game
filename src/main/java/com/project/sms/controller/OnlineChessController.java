package com.project.sms.controller;

import com.project.sms.model.Student;
import com.project.sms.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/chess")
public class OnlineChessController {
    @Autowired
    StudentService studentService;


    @RequestMapping("/")
    public String getAll(Model model) {
        List<Student> students = studentService.findAll();
        model.addAttribute("students", students);
        return "sockets/index";
    }

}
