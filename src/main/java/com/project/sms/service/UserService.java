package com.project.sms.service;

import com.project.sms.model.Login;
import com.project.sms.model.User;
import com.project.sms.repository.LoginRepository;
import com.project.sms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthService authService;
    @Autowired
    AddressService addressService;
    @Autowired
    LoginRepository loginRepository;
    public void createUser(User user) {
        //userRepository.save(user);
        authService.saveLogin(user.getLogin(), "USER");
        addressService.saveAddress(user.getAddress());
        userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
    public boolean updateUser(String id, User user) {
        User user1 = userRepository.findById(id).get();
        Login login = authService.findByEmail(user.getLogin().getEmail());
        if(login != null && !user1.getLogin().getEmail().matches(login.getEmail())) {
            return false;
        }
        user.getLogin().setId(user1.getLogin().getId());
        user.getLogin().setRoles(user1.getLogin().getRoles());
        user.getAddress().setId(user1.getAddress().getId());
        authService.saveLogin(user.getLogin(), "USER");
        addressService.saveAddress(user.getAddress());
        userRepository.save(user);
        return true;
    }
    public void deleteUser(String id) {
        User user = userRepository.findById(id).get();
        authService.deleteById(user.getLogin().getId());
        addressService.deleteById(user.getAddress().getId());
        userRepository.deleteById(id);
    }
    public User findById(String id) {
        Optional<User> userOptional =  userRepository.findById(id);
        return userOptional.get();
    }
    public User findByEmail(String email) {
        Login login = loginRepository.findByEmail(email);
        User user =  userRepository.findByLogin(login);
        return user;
    }

}
