package com.example.fortunecookie.service;

import com.example.fortunecookie.dto.FraseSorte;
import com.example.fortunecookie.repositorio.FraseRepositorio;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {"chatgpt.apiKey=valor_teste"})
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
class FortuneCookieServiceTest {

    @Autowired
    FortuneCookieService fortuneCookieService;

    @MockitoBean
    private FraseRepositorio fraseRepositorio;

    @TestConfiguration
    static class MeterRegistryConfig {
        @Bean
        public MeterRegistry meterRegistry() {
            return new SimpleMeterRegistry();
        }
    }

    @Test
    void deveRetornarFraseSorteValida() {
        FraseSorte frase = fortuneCookieService.sorteiaFrase();
        assertNotNull(frase);
        assertNotNull(frase.getFraseSorteada());
        assertFalse(frase.getFraseSorteada().isEmpty());
    }


    @Test
    void deveRetornarNumeroSorteValido() {
        String numeroSorte = fortuneCookieService.sorteiaNumero("100");
        assertNotNull(numeroSorte);
        assertTrue(numeroSorte.startsWith("Seu número da sorte é : "));
    }

    @Test
    void deveLancarExceptionParaNumeroInvalido() {
        assertThrows(NumberFormatException.class, () -> {
            fortuneCookieService.sorteiaNumero("abc");
        });
    }

}
