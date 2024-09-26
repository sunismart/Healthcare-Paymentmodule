package com.Payment.PaymentIntegration.Entity;

import java.time.LocalDateTime;


import jakarta.persistence.*;
import java.time.DayOfWeek;

@Entity
public class Slot {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private DayOfWeek day; // Represents the day of the week

    private LocalDateTime startTime;
    private LocalDateTime endTime;


    @ManyToOne // Each slot is linked to one provider
    @JoinColumn(name = "provider_id") // This column will store the provider ID
    private Provider provider;

    // Default constructor
    public Slot() {
    	
    }
    
    public Slot(Long id, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Parameterized constructor
    public Slot(DayOfWeek day, LocalDateTime startTime, LocalDateTime endTime) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }
}
