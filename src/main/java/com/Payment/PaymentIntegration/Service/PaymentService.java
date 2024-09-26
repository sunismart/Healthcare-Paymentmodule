package com.Payment.PaymentIntegration.Service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.Payment.PaymentIntegration.Entity.Appointment;
import com.Payment.PaymentIntegration.Entity.Enum.AppointmentStatus;
import com.Payment.PaymentIntegration.Entity.Enum.PaymentMethod;
import com.Payment.PaymentIntegration.Entity.Enum.PaymentStatus;
import com.Payment.PaymentIntegration.Entity.Patient;
import com.Payment.PaymentIntegration.Entity.Payment;
import com.Payment.PaymentIntegration.Entity.Invoice;
import com.Payment.PaymentIntegration.Repository.AppointmentRepository;
import com.Payment.PaymentIntegration.Repository.InvoiceRepository;
import com.Payment.PaymentIntegration.Repository.PaymentRepository;
import com.Payment.PaymentIntegration.feign.NotificationFeign;
import com.Payment.PaymentIntegration.response.CommonResponse;
import com.razorpay.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

 // Razorpay integration
    private final RazorpayClient razorpayClient;
    
    @Autowired
    private NotificationFeign notificationFeign;

    // Constructor injection of RazorpayClient
    @Autowired
    public PaymentService(RazorpayClient razorpayClient) {
        this.razorpayClient = razorpayClient;
    }

    public CommonResponse<Invoice> generateInvoice(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found with id: " + appointmentId));

        if (appointment.getStatus() != AppointmentStatus.COMPLETED) {
            return new CommonResponse<>(HttpStatus.BAD_REQUEST, 
                "Invoice can only be generated for completed appointments", null);
        }
        return generateInvoice(appointment);
    }


    public CommonResponse<Invoice> generateInvoice(Appointment appointment) {
        Patient patient = appointment.getPatient();
        Double amount = calculateAmount(appointment);

        Invoice invoice = new Invoice(appointment, patient, amount, patient.getInsuranceDetails(), PaymentStatus.PENDING, LocalDateTime.now());
        Invoice savedInvoice = invoiceRepository.save(invoice);
        notificationFeign.sendPaymentReminder(invoice.getId());
        return new CommonResponse<>(HttpStatus.OK, "Invoice generated successfully", savedInvoice);
    }

    public CommonResponse<Payment> processPayment(Long invoiceId, PaymentMethod paymentMethod) throws RazorpayException {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        // Check if it's a card-based payment method (CREDIT_CARD or DEBIT_CARD)
        if (paymentMethod == PaymentMethod.CREDIT_CARD || paymentMethod == PaymentMethod.DEBIT_CARD) {
            // Create the order request for Razorpay
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", invoice.getAmount() * 100); // amount in paise
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "receipt_" + invoice.getId());
            Order order = razorpayClient.orders.create(orderRequest);
            String razorpayId = order.get("id"); // Extract the Razorpay Order ID

            // Save the payment with Razorpay order ID and status as Created
            Payment payment = new Payment(invoice, paymentMethod, LocalDateTime.now(), PaymentStatus.CREATED);
            payment.setRazorpayPaymentId(razorpayId);
            Payment savedPayment = paymentRepository.save(payment);
            
            return new CommonResponse<>(HttpStatus.OK, "Payment order created. Awaiting confirmation.", savedPayment);
            
            
        } else if (paymentMethod == PaymentMethod.INSURANCE || paymentMethod == PaymentMethod.CASH) {
            // Handle non-Razorpay payments (e.g., Insurance or Cash)
            Payment payment = new Payment(invoice, paymentMethod, LocalDateTime.now(), PaymentStatus.PAID);
            payment.setRazorpayPaymentId("Not Applicable");
            Payment savedPayment = paymentRepository.save(payment);
            return new CommonResponse<>(HttpStatus.OK, "Payment processed successfully", savedPayment);
        } else {
            throw new IllegalArgumentException("Invalid payment method");
        }
    }

    private Double calculateAmount(Appointment appointment) {
        return appointment.getProvider().getExperience() * 100.0;
    }

    public CommonResponse<String> updatePaymentStatus(String razorpayPaymentId, String paymentStatus) {
        Payment payment = paymentRepository.findByRazorpayPaymentId(razorpayPaymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found with order ID: " + razorpayPaymentId));

        if ("captured".equals(paymentStatus)) {
            payment.setStatus(PaymentStatus.PAID);
        } else {
            payment.setStatus(PaymentStatus.FAILED);
        }

        paymentRepository.save(payment);
        return new CommonResponse<>(HttpStatus.OK, "Payment status updated successfully", null);
    }

    public CommonResponse<List<Invoice>> getInvoicesByPatientId(Long patientId) {
        List<Invoice> invoices = invoiceRepository.findByPatientId(patientId);
        return new CommonResponse<>(HttpStatus.OK, "Invoices retrieved successfully", invoices);
    }

        public CommonResponse<Invoice> updateInvoicePaymentStatusByPaymentId(Long paymentId) {
            // Fetch payment by ID
            Optional<Payment> paymentOpt = paymentRepository.findById(paymentId);
            
            // Check if payment exists and if the status is PAID
            if (!paymentOpt.isPresent() || paymentOpt.get().getStatus() != PaymentStatus.PAID) {
                return new CommonResponse<>(HttpStatus.BAD_REQUEST, "Payment is not confirmed or does not exist", null);
            }

            // Fetch the invoice associated with the payment
            Payment payment = paymentOpt.get();
            Invoice invoice = payment.getInvoice(); // Fetch the invoice directly from payment

            if (invoice == null) {
                return new CommonResponse<>(HttpStatus.NOT_FOUND, "No invoice found for the given payment ID", null);
            }

            // Update the payment status in the invoice
            invoice.setStatus(PaymentStatus.PAID); // Update the invoice status to PAID
            Invoice updatedInvoice = invoiceRepository.save(invoice); // Save the updated invoice

            return new CommonResponse<>(HttpStatus.OK, "Invoice payment status updated to PAID", updatedInvoice);
        }
    


}
