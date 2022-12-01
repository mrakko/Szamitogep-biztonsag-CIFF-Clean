package com.example.ciffclean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.example.ciffclean")
public class CiffCleanApplication {

	public static void main(String[] args) {
		SpringApplication.run(CiffCleanApplication.class, args);
	}

}
