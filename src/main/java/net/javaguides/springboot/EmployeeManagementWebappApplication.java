package net.javaguides.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmployeeManagementWebappApplication {
	public static void main(String[] args) {
		System.setProperty("javax.net.ssl.keyStore", "classpath:client1.p12");
        System.setProperty("javax.net.ssl.keyStorePassword", "changeit");
		SpringApplication.run(EmployeeManagementWebappApplication.class, args);
		System.out.println("Employee-consumer is working.......");
	}

}
