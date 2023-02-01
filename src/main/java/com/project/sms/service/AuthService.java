package com.project.sms.service;

import com.project.sms.model.Login;
import com.project.sms.model.Role;
import com.project.sms.model.Student;
import com.project.sms.repository.LoginRepository;
import com.project.sms.repository.RoleRepository;
import com.project.sms.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Login user = loginRepository.findByEmail(email);
        if(user != null) {
            List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
            return buildUserForAuthentication(user, authorities);
        } else {
            throw new UsernameNotFoundException("username not found");
        }
    }
    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        userRoles.forEach((role) -> {
            roles.add(new SimpleGrantedAuthority(role.getRole()));
        });
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }

    private UserDetails buildUserForAuthentication(Login user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    public Login findByEmail(String email){ return loginRepository.findByEmail(email);}

    public void saveLogin(Login login,String role) {
        login.setPassword(bCryptPasswordEncoder.encode(login.getPassword()));
        Role userRole = roleRepository.findByRole(role);
        login.setRoles(new HashSet<>(Arrays.asList(userRole)));
        loginRepository.save(login);
    }
    public Login findById(String id) {

        Optional<Login> user = loginRepository.findById(id);
        return user.get();
    }
    public void deleteById(String id) { loginRepository.deleteById(id);}

}
