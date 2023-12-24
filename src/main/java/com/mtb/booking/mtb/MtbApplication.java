package com.mtb.booking.mtb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.mtb.booking.*")
@EntityScan(basePackages = "com.mtb.booking.*")
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "com.mtb.booking.*")
public class MtbApplication {

	public static void main(String[] args) {
		SpringApplication.run(MtbApplication.class, args);
	}

}
