package com.project.sms.service;

import com.project.sms.model.Attendance;
import com.project.sms.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceService {
    @Autowired
    AddressService addressService;
    @Autowired
    AttendanceRepository attendanceRepository;
    public List<Attendance> findAll() {
        return attendanceRepository.findAll();
    }



}
