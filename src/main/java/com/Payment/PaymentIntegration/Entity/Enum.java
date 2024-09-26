package com.Payment.PaymentIntegration.Entity;

public class Enum {
	
	public enum Role {
	    PATIENT,
	    PROVIDER,
	    ADMIN
	}
	
	public enum DayOfWeek {
	    MONDAY,
	    TUESDAY,
	    WEDNESDAY,
	    THURSDAY,
	    FRIDAY,
	    SATURDAY,
	    SUNDAY
	}
	
	public enum AppointmentStatus {
	    SCHEDULED,
	    CANCELLED,
	    COMPLETED
	}
	
	public enum NotificationType {
	    APPOINTMENT_REMINDER,
	    PAYMENT_REMINDER
	}
	
	public enum PaymentMethod {
	    CREDIT_CARD,
	    DEBIT_CARD,
	    INSURANCE,
	    CASH,
	    UPI
	}
	
	public enum PaymentStatus {
		CREATED,
	    PENDING,
	    PAID,
	    FAILED
	}


}
