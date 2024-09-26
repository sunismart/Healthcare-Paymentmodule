package com.Payment.PaymentIntegration.Entity;

import java.time.LocalDateTime;

import com.Payment.PaymentIntegration.Entity.Enum.PaymentStatus;

import jakarta.persistence.*;


@Entity
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    private Double amount;
    private String insuranceDetails;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private LocalDateTime issueDate;

    // Constructors, Getters, and Setters
    public Invoice() {}

    public Invoice(Appointment appointment, Patient patient, Double amount, String insuranceDetails, PaymentStatus status, LocalDateTime issueDate) {
        this.appointment = appointment;
        this.patient = patient;
        this.amount = amount;
        this.insuranceDetails = insuranceDetails;
        this.status = status;
        this.issueDate = issueDate;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getInsuranceDetails() {
		return insuranceDetails;
	}

	public void setInsuranceDetails(String insuranceDetails) {
		this.insuranceDetails = insuranceDetails;
	}

	public PaymentStatus getStatus() {
		return status;
	}

	public void setStatus(PaymentStatus status) {
		this.status = status;
	}

	public LocalDateTime getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(LocalDateTime issueDate) {
		this.issueDate = issueDate;
	}
    
}
