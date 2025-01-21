package com.example.demo;

import com.example.demo.service.MqttService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DemoApplication {
	public static void main(String[] args) {
		// Запуск Spring Boot додатку
		ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);

		// Ініціалізація MqttService через Spring
		MqttService mqttService = context.getBean(MqttService.class);
	}
}
