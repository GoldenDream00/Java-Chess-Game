package com.project.sms.service;

import com.project.sms.model.Event;
import com.project.sms.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    EventRepository eventRepository;
    @Autowired
    AddressService addressService;

    public void createEvent(Event event) {

        addressService.saveAddress(event.getAddress());
        eventRepository.save(event);
    }

    public List<Event> findAll() {
        return eventRepository.findAll();
    }
    public boolean updateEvent(String id, Event event) {
        Event event1 = eventRepository.findById(id).get();
        event.getAddress().setId(event1.getAddress().getId());
        addressService.saveAddress(event.getAddress());
        eventRepository.save(event);
        return true;
    }
    public void deleteEvent(String id) {
        Event event = eventRepository.findById(id).get();
        addressService.deleteById(event.getAddress().getId());
        eventRepository.deleteById(id);
    }
    public Event findById(String id) {
        Optional<Event> eventOptional =  eventRepository.findById(id);
        return eventOptional.get();
    }
}
