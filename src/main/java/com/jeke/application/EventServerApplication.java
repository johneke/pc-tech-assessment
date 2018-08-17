package com.jeke.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.jeke.controllers",
        "com.jeke.data",
        "com.jeke.errors",
        "com.jeke.helpers"
})
public class EventServerApplication {
	public static void main(String[] args) {
        SpringApplication.run(EventServerApplication.class, args);
    }
}
