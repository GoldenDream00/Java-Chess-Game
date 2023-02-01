package com.project.sms.controller;

import com.project.sms.model.Attendance;
import com.project.sms.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/attendance/")
public class AttendanceController {
    @Autowired
    AttendanceService attendanceService;
    @RequestMapping("/")
    public String getAll(Model model) {
        List<Attendance> attendances = attendanceService.findAll();
        model.addAttribute("attendances", attendances);
        return "attendance/list";
    }
    @RequestMapping(value = "/create")
    public String createAttendance(Model model) {
        Attendance attendance = new Attendance();
        model.addAttribute("attendance", attendance);
        return "attendance/create";
    }
//    @PostMapping("/store")
//    public String storeAttendance(Attendance attendance) {
//        attendanceService.createAttendance(attendance);
//        return "redirect:/attendance/";
//    }
//    @GetMapping("/delete/{id}")
//    public String deleteAttendance(@PathVariable(value = "id") String id) {
//        attendanceService.deleteAttendance(id);
//        return "redirect:/attendance/";
//    }
//    @GetMapping("/update/{id}")
//    public String updateAttendanceView(@PathVariable(value = "id") String id, Model model) {
//        Attendance attendance = attendanceService.findById(id);
//        model.addAttribute("attendance", attendance);
//        return "attendance/update";
//    }
//    @PostMapping("/update/{id}")
//    public String updateAttendance(@PathVariable(value = "id") String id,Attendance attendance) {
//        attendanceService.updateAttendance(id, attendance);
//        return "redirect:/attendance/";
//    }
}
