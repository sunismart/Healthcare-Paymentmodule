package com.Payment.PaymentIntegration.feign;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name ="Notification",url = "http://localhost:8085/notifications")
public interface NotificationFeign {
	
	@PostMapping("/Payment-reminder")
    public String sendPaymentReminder(@RequestParam Long invoiceId);
}
