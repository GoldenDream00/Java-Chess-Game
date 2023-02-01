package com.project.sms.repository;

import com.project.sms.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface EventRepository extends MongoRepository<Event, String> {
}
