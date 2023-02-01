package com.project.sms.repository;

import com.project.sms.model.Attendance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends MongoRepository<Attendance, String> {
}
