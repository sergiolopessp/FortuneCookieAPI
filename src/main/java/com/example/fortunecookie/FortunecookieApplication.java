package com.example.fortunecookie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.context.annotation.Import;
import com.maciejwalkowiak.spring.boot.startup.StartupEventsAutoConfiguration;

@SpringBootApplication
@Import(StartupEventsAutoConfiguration.class)
public class FortunecookieApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(FortunecookieApplication.class);
		app.setApplicationStartup(new BufferingApplicationStartup(2048));
		app.run(args);
	}

}
