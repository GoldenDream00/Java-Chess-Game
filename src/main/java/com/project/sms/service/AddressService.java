package com.project.sms.service;

import com.project.sms.model.Address;
import com.project.sms.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    @Autowired
    AddressRepository addressRepository;

    public void saveAddress(Address address) {
        addressRepository.save(address);
    }
    public void deleteById(String id) { addressRepository.deleteById(id);}
}
