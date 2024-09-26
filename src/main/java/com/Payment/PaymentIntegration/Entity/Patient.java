package com.Payment.PaymentIntegration.Entity;
import java.time.LocalDate;

import com.Payment.PaymentIntegration.Entity.Enum.Role;

import jakarta.persistence.Entity;

@Entity
public class Patient extends User {

    private String medicalHistory;
    private String insuranceDetails;

    public Patient() {}

    public Patient(String firstName, String lastName, String email, String password, Role role, String phoneNumber, String address, LocalDate dateOfBirth, String medicalHistory, String insuranceDetails) {
        super(firstName, lastName, email, password, role, phoneNumber, address, dateOfBirth);
        this.medicalHistory = medicalHistory;
        this.insuranceDetails = insuranceDetails;
    }
    
    public Patient(Long id) {
        super(); // Call the superclass constructor
        this.setId(id); // Set the ID
    }

    // Getters and Setters
    public String getMedicalHistory() {
        return medicalHistory;
    }
    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }
    public String getInsuranceDetails() {
        return insuranceDetails;
    }
    public void setInsuranceDetails(String insuranceDetails) {
        this.insuranceDetails = insuranceDetails;
    }
}
