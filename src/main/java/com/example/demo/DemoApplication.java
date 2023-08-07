package com.example.demo;

import org.springframework.boot.SpringApplication;
// import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
	
	// @GetMapping
	// public String message(){
	// 	return "welcome to devops pipeline";
	// }

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
