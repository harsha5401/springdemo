package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
	
	@RestController
public class HelloController {

    @GetMapping("/dxc")
    public String helloWorld() {
        return "Hello,using ecs!";
    }
}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
