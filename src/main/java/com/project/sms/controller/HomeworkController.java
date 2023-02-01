package com.project.sms.controller;

import com.project.sms.model.*;
import com.project.sms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/homework/")
public class HomeworkController {
    @Autowired
    TeacherService teacherService;
    @Autowired
    StudentService studentService;
    @Autowired
    HomeworkService homeworkService;
    @Autowired
    PuzzleService puzzleService;
    @Autowired
    AuthService authService;

    @GetMapping("/")
    public String index(Model model) {
        List<Homework> homeworks = homeworkService.findAll();
        model.addAttribute("homeworks", homeworks);
        return "homework/list";
    }
    @GetMapping("/create")
    public String createHomework(Model model) {
        Homework homework = new Homework();
        List<Teacher> teachers = teacherService.findAll();
        List<Student> students = studentService.findAll();
        List<Puzzle> puzzles =puzzleService.findAll();
        model.addAttribute("homework", homework);
        model.addAttribute("teachers", teachers);
        model.addAttribute("students", students);
        model.addAttribute("puzzles", puzzles);
        return "homework/create";
    }

    @PostMapping("/store")
    public String storeHomework(Homework homework, @RequestParam(value ="student") String studentId, @RequestParam(value ="teacher", required = false) String teacherId,@RequestParam(value ="puzzle") List<String> puzzleID ) {
        homeworkService.createHomework(studentId, teacherId, puzzleID, homework);
        return "redirect:/homework/";
    }

    @GetMapping("/delete/{id}")
    public String deleteHomework(@PathVariable(value = "id") String id) {
        homeworkService.deleteHomework(id);
        return "redirect:/homework/";
    }
    @GetMapping("/update/{id}")
    public String updateHomeworkView(@PathVariable(value = "id") String id, Model model) {
        Homework homework = homeworkService.findById(id);
        List<Teacher> teachers = teacherService.findAll();
        List<Student> students = studentService.findAll();
        List<Puzzle> puzzles =puzzleService.findAll();
        model.addAttribute("homework", homework);
        model.addAttribute("teachers", teachers);
        model.addAttribute("students", students);
        model.addAttribute("puzzle", homework);
        model.addAttribute("puzzles", puzzles);
        return "homework/update";
    }
    @PostMapping("/update/{id}")
    public String updateHomework(@PathVariable(value = "id") String id,Homework homework, @RequestParam(value ="student") String studentId, @RequestParam(value ="teacher", required = false) String teacherId, @RequestParam(value ="puzzle") List<String> puzzleID) {
        homeworkService.updateHomework(id, homework, studentId, teacherId, puzzleID);
        return "redirect:/homework/";
    }

    @GetMapping("/individual")
    public String individual(Model model) {
        List<Homework> homeworks = homeworkService.findAllByStudentId();
        model.addAttribute("homeworks", homeworks);

        return "homework/individual";
    }
}
