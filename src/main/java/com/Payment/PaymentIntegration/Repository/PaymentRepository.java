package com.Payment.PaymentIntegration.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Payment.PaymentIntegration.Entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
	Optional<Payment> findByRazorpayPaymentId(String razorpayPaymentId);
}
