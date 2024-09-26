package com.Payment.PaymentIntegration.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.Payment.PaymentIntegration.Entity.Invoice;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByPatientId(Long patientId);
}
