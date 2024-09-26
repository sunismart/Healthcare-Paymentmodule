package com.Payment.PaymentIntegration.Controller;
import java.util.List;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.Payment.PaymentIntegration.Entity.Enum.PaymentMethod;
import com.Payment.PaymentIntegration.Entity.Invoice;
import com.Payment.PaymentIntegration.Entity.Payment;
import com.Payment.PaymentIntegration.Service.PaymentService;
import com.Payment.PaymentIntegration.response.CommonResponse;
import com.razorpay.RazorpayException;

@RestController
@RequestMapping("/api/payments")
public class PaymentWebhookController {

    @Autowired
    private PaymentService paymentService;
    
    @PostMapping("/generate-invoice/{appointmentId}")
    public ResponseEntity<CommonResponse<Invoice>> generateInvoice(@PathVariable Long appointmentId) {
        CommonResponse<Invoice> invoice = paymentService.generateInvoice(appointmentId);
        return ResponseEntity.ok(invoice);
    }
    
    @PostMapping("/process-payment/{invoiceId}")
    public ResponseEntity<CommonResponse<Payment>> processPayment(
            @PathVariable Long invoiceId,
            @RequestParam PaymentMethod paymentMethod) throws RazorpayException {
        CommonResponse<Payment> payment = paymentService.processPayment(invoiceId, paymentMethod);
        return ResponseEntity.ok(payment);
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleRazorpayWebhook(@RequestBody Map<String, Object> payload) {
        try {
            String razorpayPaymentId = (String) payload.get("razorpay_payment_id");
            String paymentStatus = (String) payload.get("status");
            
            paymentService.updatePaymentStatus(razorpayPaymentId, paymentStatus);
            return ResponseEntity.ok("Payment processed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing payment");
        }
    }
    
    @GetMapping("/invoices/{patientId}")
    public ResponseEntity<CommonResponse<List<Invoice>>> getInvoicesByPatientId(@PathVariable Long patientId) {
        CommonResponse<List<Invoice>> invoices = paymentService.getInvoicesByPatientId(patientId);
        return ResponseEntity.ok(invoices);
    }
    
    @PutMapping("/update-invoice-status/{paymentId}")
    public ResponseEntity<CommonResponse<Invoice>> updateInvoicePaymentStatus(@PathVariable Long paymentId) {
        CommonResponse<Invoice> response = paymentService.updateInvoicePaymentStatusByPaymentId(paymentId);
        
        return new ResponseEntity<>(response, response.getStatus());
    }
    
    @GetMapping("/test")
    public String test() {
    	return "Backend Started...";
    } 
}
