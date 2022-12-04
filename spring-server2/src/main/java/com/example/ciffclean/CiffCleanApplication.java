package com.example.ciffclean;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

import com.example.ciffclean.domain.AppUser;
import com.example.ciffclean.repositories.UserRepository;

@SpringBootApplication
@EntityScan("com.example.ciffclean")
public class CiffCleanApplication {

	public static void main(String[] args) {
		SpringApplication.run(CiffCleanApplication.class, args);
	}

	@Bean
	public CommandLineRunner registerAnAdmin(UserRepository repo) {
		return args -> { 
			AppUser admin = new AppUser();
			admin.setEmail("admin@admin.com");
			admin.setFullName("Admin");
			admin.setAddress("Admin address");
			admin.setPassword("AdminPasswordThatIsVerySecret123");
            repo.save(admin);
        };
	}

}
