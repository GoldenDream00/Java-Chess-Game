package com.project.sms;

import com.project.sms.model.Login;
import com.project.sms.model.Role;
import com.project.sms.repository.LoginRepository;
import com.project.sms.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
@EnableMongoAuditing
public class StudentManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentManagementSystemApplication.class, args);
	}
	@Bean
	CommandLineRunner init(RoleRepository roleRepository, LoginRepository loginRepository) {

		return args -> {

			Role adminRole = roleRepository.findByRole("ADMIN");
			if (adminRole == null) {
				Role newAdminRole = new Role();
				newAdminRole.setRole("ADMIN");
				roleRepository.save(newAdminRole);
			}

			Role userRole = roleRepository.findByRole("USER");
			if (userRole == null) {
				Role newUserRole = new Role();
				newUserRole.setRole("USER");
				roleRepository.save(newUserRole);
			}

			Role teacherRole = roleRepository.findByRole("TEACHER");
			if (teacherRole == null) {
				Role newUserRole = new Role();
				newUserRole.setRole("TEACHER");
				roleRepository.save(newUserRole);
			}

			Role studentRole = roleRepository.findByRole("STUDENT");
			if (studentRole == null) {
				Role newUserRole = new Role();
				newUserRole.setRole("STUDENT");
				roleRepository.save(newUserRole);
			}
			Role guardianRole = roleRepository.findByRole("GUARDIAN");
			if (guardianRole == null) {
				Role newUserRole = new Role();
				newUserRole.setRole("GUARDIAN");
				roleRepository.save(newUserRole);
			}

			Role newRole = roleRepository.findByRole("ADMIN");
			Set<Role> newRoleSet = new HashSet<>(Arrays.asList(newRole));
			List<Login> adminLogin = loginRepository.findByRolesContains(newRoleSet);

			if (adminLogin.isEmpty()) {
				Login login = new Login();
				login.setRoles(newRoleSet);
				login.setEmail("admin@gmail.com");
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				login.setPassword(encoder.encode("admin"));
				loginRepository.save(login);

			}
		};

	}
//


}
