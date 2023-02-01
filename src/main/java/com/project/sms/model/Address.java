package com.project.sms.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Address")
public class Address {
    @Id
    private String id;
    private String Country;
    private String city;
    private String street;
    private String zip;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getCountry() {
        return Country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreet() {
        return street;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getZip() {
        return zip;
    }
}
