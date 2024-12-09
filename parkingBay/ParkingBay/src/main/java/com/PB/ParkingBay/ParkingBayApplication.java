package com.PB.ParkingBay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.PB.ParkingBay")
public class ParkingBayApplication {

	public static void main(String[] args) {
		System.out.println("✅ Main App ✅");
		SpringApplication.run(ParkingBayApplication.class, args);
	}

}
