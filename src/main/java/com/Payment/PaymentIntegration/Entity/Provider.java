package com.Payment.PaymentIntegration.Entity;


import java.time.LocalDate;



import java.util.List;

import com.Payment.PaymentIntegration.Entity.Enum.Role;

import jakarta.persistence.*;

@Entity
public class Provider extends User {

    private String specialization;
    private String clinicAddress;

    @ElementCollection
    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Slot> availableSlots;
    private int experience;

    public Provider() {}

    public Provider(String firstName, String lastName, String email, String password, Role role, String phoneNumber, String address, LocalDate dateOfBirth, String specialization, String clinicAddress, List<Slot> availableSlots, int experience) {
        super(firstName, lastName, email, password, role, phoneNumber, address, dateOfBirth);
        this.specialization = specialization;
        this.clinicAddress = clinicAddress;
        this.availableSlots = availableSlots;
        this.experience = experience;
    }
    
    public Provider(Long id) {
        super(); // Call the superclass constructor
        this.setId(id); // Set the ID
    }

    // Getters and Setters
    public String getSpecialization() {
        return specialization;
    }
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
    public String getClinicAddress() {
        return clinicAddress;
    }
    public void setClinicAddress(String clinicAddress) {
        this.clinicAddress = clinicAddress;
    }
    public List<Slot> getAvailableSlots() {
        return availableSlots;
    }
    public void setAvailableSlots(List<Slot> availableSlots) {
        this.availableSlots = availableSlots;
    }
    public int getExperience() {
        return experience;
    }
    public void setExperience(int experience) {
        this.experience = experience;
    }
}
