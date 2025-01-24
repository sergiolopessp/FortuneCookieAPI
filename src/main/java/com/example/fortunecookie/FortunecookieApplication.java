package com.example.fortunecookie;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.logging.LoggingMeterRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FortunecookieApplication {

	public static void main(String[] args) {
		SpringApplication.run(FortunecookieApplication.class, args);
	}

}
