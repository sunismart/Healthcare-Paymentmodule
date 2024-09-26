package com.Payment.PaymentIntegration.Entity;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

import com.Payment.PaymentIntegration.Entity.Enum.PaymentMethod;
import com.Payment.PaymentIntegration.Entity.Enum.PaymentStatus;


@Entity
public class Payment {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(name = "razorpay_payment_id")
	    private String razorpayPaymentId;

	    @ManyToOne
	    @JoinColumn(name = "invoice_id")
	    @NotNull(message = "Invoice is required")
	    private Invoice invoice;

	    @Enumerated(EnumType.STRING)
	    @NotNull(message = "Payment method is required")
	    private PaymentMethod paymentMethod;

	    @NotNull(message = "Payment date is required")
	    private LocalDateTime paymentDate;

	    @Enumerated(EnumType.STRING)
	    @NotNull(message = "Payment status is required")
	    private PaymentStatus status;


    public Payment() {}

	public Payment(Invoice invoice, PaymentMethod paymentMethod,
			LocalDateTime paymentDate, PaymentStatus status) {
		super();		
		this.invoice = invoice;
		this.paymentMethod = paymentMethod;
		this.paymentDate = paymentDate;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRazorpayPaymentId() {
		return razorpayPaymentId;
	}

	public void setRazorpayPaymentId(String razorpayPaymentId) {
		this.razorpayPaymentId = razorpayPaymentId;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public LocalDateTime getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDateTime paymentDate) {
		this.paymentDate = paymentDate;
	}

	public PaymentStatus getStatus() {
		return status;
	}

	public void setStatus(PaymentStatus status) {
		this.status = status;
	}

}