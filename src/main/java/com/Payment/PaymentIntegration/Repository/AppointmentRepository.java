package com.Payment.PaymentIntegration.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Payment.PaymentIntegration.Entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
