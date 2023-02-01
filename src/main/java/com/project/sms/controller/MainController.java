package com.project.sms.controller;

import com.project.sms.model.*;
import com.project.sms.repository.RoleRepository;
import com.project.sms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Controller
public class MainController {

    @Autowired
    AuthService authService;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    TeacherService teacherService;
    @Autowired
    GuardianService guardianService;
    @Autowired
    StudentService studentService;
    @Autowired
    UserService userService;
    @RequestMapping("/")
    public ModelAndView Home() {

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("home");
        return modelAndView;
    }
    @RequestMapping("/welcome")
    public String Welcome() {
        return "welcome";
    }

    @RequestMapping("/about")
    public String About() {
        return "about";
    }
}
