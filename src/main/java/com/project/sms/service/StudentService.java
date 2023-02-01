package com.project.sms.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.project.sms.model.Guardian;
import com.project.sms.model.Login;
import com.project.sms.model.Student;
import com.project.sms.model.Teacher;
import com.project.sms.repository.LoginRepository;
import com.project.sms.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

@Service
public class StudentService {

	@Autowired
	AuthService authService;
	@Autowired
	AddressService addressService;
	@Autowired
	GuardianService guardianService;
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	LoginRepository loginRepository;
	//Get the list of students each by guardian.
	public List<Student> findAll() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // get loggged in user
		Guardian guardian = guardianService.findByEmail(authentication.getName());
		//If admin, then get all of students info.
		Collection<?extends GrantedAuthority> granted = authentication.getAuthorities();
		if(!(granted.toArray()[0]+"").matches("GUARDIAN")) {
			return studentRepository.findAll();
		}
		List<Guardian> guardians = new ArrayList<Guardian>();
		guardians.add(guardian);
		return studentRepository.findByGuardianContains(guardians);

	}
	public void createStudent(Student student, List<String> guardianID) {
		if(guardianID!=null) {
			List<Guardian> guardians= guardianService.findAllByIDs(guardianID);
			student.setGuardian(guardians);
			studentRepository.save(student);
			return;
		}
		//if user logged as a guardian
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // get loggged in user
		Guardian guardian = guardianService.findByEmail(authentication.getName());
		List<Guardian> guardians = new ArrayList<Guardian>();
		guardians.add(guardian);
		student.setGuardian(guardians);
		studentRepository.save(student);
	}
	//update student
	//if logged as admin, we choose guardians.
	//if logged as a guardian, guardian will be gotten from authentication.
	public boolean updateStudent(String id, Student student , List<String> guardianID) {
		Student student1 = studentRepository.findById(id).get();
		student.setId(student1.getId());
		student.setRegistrationDate(student1.getRegistrationDate());
		if(guardianID!=null) {
			List<Guardian> guardians= guardianService.findAllByIDs(guardianID);
			student.setGuardian(guardians);
		} else {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // get loggged in user
			Guardian guardian = guardianService.findByEmail(authentication.getName());
			List<Guardian> guardians = new ArrayList<Guardian>();
			guardians.add(guardian);
			student.setGuardian(guardians);
		}
		studentRepository.save(student);
		return true;
	}
	public void deleteStudent(String id) {
		Student student = studentRepository.findById(id).get();
		studentRepository.deleteById(id);
	}
	public Student findById(String id) {
		Optional<Student> studentOptional =  studentRepository.findById(id);
		return studentOptional.get();
	}
	//get list of student give by a guardian.
	public List<Student> findAllByGuardian(Guardian guardian){
		List<Guardian> guardians = new ArrayList<Guardian>();
		guardians.add(guardian);
		return studentRepository.findByGuardianContains(guardians);
	}
	public Student findByName(String name) {
		Student student =  studentRepository.findByNickname(name);
		return student;
	}
	public List<Student> findAllWithoutGuardian() {
		List<Student> students = studentRepository.findAll();
		return  students;
	}
}
