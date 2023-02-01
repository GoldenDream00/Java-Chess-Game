package com.project.sms.controller;

import java.util.List;

import com.project.sms.model.Guardian;
import com.project.sms.service.GuardianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.project.sms.model.Student;
import com.project.sms.service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentController {

	@Autowired
	StudentService studentService;
	@Autowired
	GuardianService guardianService;
	/*
	* url http://localhost/students/
	* method GET
	* desc shows all of student information
	* */

	@RequestMapping("/")
	public String getAll(Model model) {
		List<Student> students = studentService.findAll();
		model.addAttribute("students", students);
		return "students/list";
	}
	@RequestMapping(value = "/create")
	public String saveStudent(Model model) {
		Student student = new Student();
		List<Guardian> guardians = guardianService.findAll();
		model.addAttribute("guardians", guardians);
		model.addAttribute("student", student);
		return "students/create";
	}
	@PostMapping(value = "/store")//step 2: register student and redirect to event.
	public String saveStudent(Student student,@RequestParam(value = "guardian", required = false) List<String> guardianID) {
		studentService.createStudent(student, guardianID);
		return "redirect:/students/";
	}
	@GetMapping("/delete/{id}")
	public String deleteTeacher(@PathVariable(value = "id") String id) {
		studentService.deleteStudent(id);
		return "redirect:/students/";
	}
	@GetMapping("/update/{id}")
	public String updateStudentView(@PathVariable(value = "id") String id, Model model) {
		Student student = studentService.findById(id);
		model.addAttribute("student", student);
		List<Guardian> guardians = guardianService.findAll();
		model.addAttribute("guardians", guardians);
		return "students/update";
	}
	@PostMapping("/update/{id}")
	public String updateStudent(@PathVariable(value = "id") String id, Student student,@RequestParam(value ="guardian", required = false) List<String> guardianID) {
		studentService.updateStudent(id, student, guardianID);
		return "redirect:/students/";
	}
}
