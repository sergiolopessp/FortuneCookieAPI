package com.example.fortunecookie.configuration;

import org.ff4j.FF4j;
import org.ff4j.core.Feature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FF4jConfig {
    public static final String IA_FEATURE = "IA_FEATURE";

    @Bean
    public FF4j ff4j() {
            FF4j ff4j = new FF4j();
            Feature iaFeature = new Feature(IA_FEATURE);
            iaFeature.disable();
            ff4j.createFeature(iaFeature);
            return ff4j;
    }

}
