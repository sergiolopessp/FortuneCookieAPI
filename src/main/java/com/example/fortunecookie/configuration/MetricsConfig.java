package com.example.fortunecookie.configuration;

import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.config.MeterFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class MetricsConfig {

    @Bean
    MeterFilter commonTagsMeterFilter() {
        return MeterFilter.commonTags(Arrays.asList(Tag.of("application", "fortune-cookie")));
    }
}