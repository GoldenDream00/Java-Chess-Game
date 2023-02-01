package com.project.sms.controller;

import com.project.sms.model.Guardian;
import com.project.sms.model.User;
import com.project.sms.service.GuardianService;
import com.project.sms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users/")
public class UserController {
    @Autowired
    UserService userService;
    @RequestMapping("/")
    public String getAll(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "users/list";
    }
    @RequestMapping(value = "/create")
    public String createUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "users/create";
    }
    @PostMapping("/store")
    public String storeUser(User user) {
        userService.createUser(user);
        return "redirect:/users/";
    }
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable(value = "id") String id) {
        userService.deleteUser(id);
        return "redirect:/users/";
    }
    @GetMapping("/update/{id}")
    public String updateUserView(@PathVariable(value = "id") String id, Model model) {
        User user = userService.findById(id);

        user.getLogin().setPassword("");
        model.addAttribute("user", user);
        return "users/update";
    }
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable(value = "id") String id,User user) {
        userService.updateUser(id, user);
        return "redirect:/users/";
    }
}
