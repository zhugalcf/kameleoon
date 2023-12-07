package com.zhugalcf.kameleoon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class KameleoonApplication {

	public static void main(String[] args) {
		SpringApplication.run(KameleoonApplication.class, args);
	}

}
