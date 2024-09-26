package com.Payment.PaymentIntegration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PaymentIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentIntegrationApplication.class, args);
	}

}
