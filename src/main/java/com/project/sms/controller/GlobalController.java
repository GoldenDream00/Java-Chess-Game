package com.project.sms.controller;

import com.project.sms.model.Guardian;
import com.project.sms.model.Student;
import com.project.sms.model.Teacher;
import com.project.sms.model.User;
import com.project.sms.service.GuardianService;
import com.project.sms.service.StudentService;
import com.project.sms.service.TeacherService;
import com.project.sms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;


/*
* Desc: We use "username" model attribute to global so that any template can use.
* */
@ControllerAdvice
public class GlobalController {
    @Autowired
    StudentService studentService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    GuardianService guardianService;
    @Autowired
    UserService userService;
    @ModelAttribute
    public void setGlobalModelAttributes(HttpServletRequest request, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // get loggged in user
        Collection<?extends GrantedAuthority> granted = authentication.getAuthorities(); // get logged user's roles
        for ( int i = 0; i < granted.size(); i ++) {
            String role = granted.toArray()[i]+"";
            if(role.matches("ADMIN")) { // if admin -> name would be name
                String username = "ADMIN";
                model.addAttribute("username", "Admin");
            }
            //If role is teacher -> then find Teacher who has got email and set name to teacher's name
            if ( role.matches("TEACHER")) {
                Teacher teacher = teacherService.findByEmail(authentication.getName());
                model.addAttribute("username",teacher.getFirstName());
            }
            //If role is teacher -> then find Teacher who has got email and set name to teacher's name
            if( role.matches("STUDENT")) {
                //Student student = studentService.findByEmail(authentication.getName());
                //model.addAttribute("username",student.getNickname());
            }
            //If role is teacher -> then find Teacher who has got email and set name to teacher's name
            if (role.matches("GUARDIAN")) {
                Guardian guardian = guardianService.findByEmail(authentication.getName());
                model.addAttribute("username",guardian.getFirstName());
            }
            //If role is teacher -> then find Teacher who has got email and set name to teacher's name
            if (role.matches("USER")) {
                User user = userService.findByEmail(authentication.getName());
                model.addAttribute("username",user.getName());
            }
        }
//       model.addAttribute("username", "Hello");
    }
}
