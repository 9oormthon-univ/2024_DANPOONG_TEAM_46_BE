package com.goormthon.bookduchilseong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BookduchilseongApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookduchilseongApplication.class, args);
	}

}
