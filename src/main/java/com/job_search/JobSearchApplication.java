package com.job_search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.job_search"})
public class JobSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobSearchApplication.class, args);
	}

}
