package com.project.sms.controller;

import com.project.sms.model.Guardian;
import com.project.sms.service.GuardianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/guardians/")
public class GuardianController {

    @Autowired
    GuardianService guardianService;
    @RequestMapping("/")
    public String getAll(Model model) {
        List<Guardian> guardians = guardianService.findAll();
        model.addAttribute("guardians", guardians);
        return "guardians/list";
    }
    @RequestMapping(value = "/create")
    public String createGuardian(Model model) {
        Guardian guardian = new Guardian();
        model.addAttribute("guardian", guardian);
        return "guardians/create";
    }
    @PostMapping("/store")//Is redirecting once the guardian is created to the students/create. step 1: Create Guardian
    public String storeGuardian(Guardian guardian) {
        guardianService.createGuardian(guardian);
        return "redirect:/students/create";
    }
    @GetMapping("/delete/{id}")
    public String deleteGuardian(@PathVariable(value = "id") String id) {
        guardianService.deleteGuardian(id);
        return "redirect:/guardians/";
    }
    @GetMapping("/update/{id}")
    public String updateGuardianView(@PathVariable(value = "id") String id, Model model) {
        Guardian guardian = guardianService.findById(id);
        guardian.getLogin().setPassword("");
        model.addAttribute("guardian", guardian);
        return "guardians/update";
    }
    @PostMapping("/update/{id}")
    public String updateGuardian(@PathVariable(value = "id") String id,Guardian guaridian) {
        guardianService.updateGuardian(id, guaridian);
        return "redirect:/guardians/";
    }
}
