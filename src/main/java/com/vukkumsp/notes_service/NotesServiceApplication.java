package com.vukkumsp.notes_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class NotesServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotesServiceApplication.class, args);
	}

}
