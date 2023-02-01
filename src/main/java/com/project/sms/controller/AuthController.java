package com.project.sms.controller;

import com.project.sms.model.Login;
import com.project.sms.model.Student;
import com.project.sms.model.User;
import com.project.sms.payload.LoginRequest;
import com.project.sms.service.AuthService;
import com.project.sms.service.StudentService;
import com.project.sms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthController {
    @Autowired
    AuthService authService;
    @Autowired
    UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("auth/login");
        return modelAndView;
    }

    @RequestMapping(value="/signup", method = RequestMethod.GET)
    public ModelAndView signup() {
        User user = new User();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("auth/signup");
        modelAndView.addObject(user);
        return modelAndView;
    }

    @RequestMapping(value="/signup", method = RequestMethod.POST)
    public String createUser(User user) {
        Login login = authService.findByEmail(user.getLogin().getEmail());
        if(login == null) {
            userService.createUser(user);
            return "redirect:/login";
        }
        else {
            return "redirect:/signup";
        }
    }
}
