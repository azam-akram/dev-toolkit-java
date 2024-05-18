package com.azamakram.github.uuidgenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.UUID;

@RestController
@RequestMapping(path = "/")
@EnableDiscoveryClient
@SpringBootApplication
public class UuidgeneratorApplication {
	public static void main(String[] args) {
		SpringApplication.run(UuidgeneratorApplication.class, args);
	}

	@GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public String generateUuid() {
        return UUID.randomUUID().toString();
    }
}
