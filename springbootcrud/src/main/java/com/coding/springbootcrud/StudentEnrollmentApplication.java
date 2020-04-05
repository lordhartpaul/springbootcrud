package com.coding.springbootcrud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@EnabledJpaAuditing

public class StudentEnrollmentApplication {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		SpringApplication.run(StudentEnrollmentApplication.class, args);
		
	}

}
