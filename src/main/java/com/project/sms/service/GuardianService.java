package com.project.sms.service;

import com.project.sms.model.Guardian;
import com.project.sms.model.Login;
import com.project.sms.repository.GuardianRepository;
import com.project.sms.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Guard;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class GuardianService {
    @Autowired
    GuardianRepository guardianRepository;
    @Autowired
    AuthService authService;
    @Autowired
    AddressService addressService;
    @Autowired
    LoginRepository loginRepository;
    public List<Guardian> findAll() {
        return guardianRepository.findAll();
    }
    public void createGuardian(Guardian guardian) {
        authService.saveLogin(guardian.getLogin(), "GUARDIAN");
        addressService.saveAddress(guardian.getAddress());
        guardianRepository.save(guardian);
    }
    public boolean updateGuardian(String id, Guardian guardian) {
        Guardian guardian1 = guardianRepository.findById(id).get();
        Login login = authService.findByEmail(guardian.getLogin().getEmail());
        if(login != null && !guardian1.getLogin().getEmail().matches(login.getEmail())) {
            return false;
        }
        guardian.getLogin().setId(guardian1.getLogin().getId());
        guardian.getLogin().setRoles(guardian1.getLogin().getRoles());
        guardian.setRegistrationDate(guardian1.getRegistrationDate());
        guardian.getAddress().setId(guardian1.getAddress().getId());
        authService.saveLogin(guardian.getLogin(), "GUARDIAN");
        addressService.saveAddress(guardian.getAddress());
        guardianRepository.save(guardian);
        return true;
    }
    public void deleteGuardian(String id) {
        Guardian guaridan = guardianRepository.findById(id).get();
        authService.deleteById(guaridan.getLogin().getId());
        addressService.deleteById(guaridan.getAddress().getId());
        guardianRepository.deleteById(id);
    }
    public Guardian findById(String id) {
        Optional<Guardian> guardianOptional =  guardianRepository.findById(id);
        return guardianOptional.get();
    }
    public List<Guardian> findAllByIDs(List<String> ids) {
        Iterable<Guardian> puzzles = guardianRepository.findAllById(ids);
        return StreamSupport.stream(puzzles.spliterator(), false).collect(Collectors.toList());
    }
    public Guardian findByEmail(String email) {
        Login login = loginRepository.findByEmail(email);
        Guardian guardian =  guardianRepository.findByLogin(login);
        return guardian;
    }
}
