package com.example.fortunecookie.controller;

import com.example.fortunecookie.configuration.FF4jConfig;
import com.example.fortunecookie.dto.FraseSorte;


import com.example.fortunecookie.service.FortuneCookieService;
import com.example.fortunecookie.service.OpenAIService;

import io.micrometer.core.instrument.MeterRegistry;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

import org.ff4j.FF4j;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(FortuneCookieController.class)
class FortuneCookieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FortuneCookieService fortuneCookieService;

    @MockitoBean
    private OpenAIService openAIService;

    @MockitoBean
    private FF4j ff4j;

    private String apiKey = "teste-key";

    @TestConfiguration
    static class MeterRegistryConfig {
        @Bean
        public MeterRegistry meterRegistry() {
            return new SimpleMeterRegistry(); // Implementação leve de MeterRegistry
        }
    }

    @Test
    void deveRetornarFraseDaIAQuandoFeatureLigada() throws Exception {
        when(ff4j.check(FF4jConfig.IA_FEATURE)).thenReturn(true);
        when(openAIService.enviaQueryModel(anyString())).thenReturn("Frase gerada pela IA");


        mockMvc.perform(get("/sorteiaFrase"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fraseSorteada").value("Frase gerada pela IA"));
    }

    @Test
    void quandoFeatureIADesligada_deveUsarServiceLocal() throws Exception {
        when(ff4j.check(FF4jConfig.IA_FEATURE)).thenReturn(false);
        when(fortuneCookieService.sorteiaFrase())
                .thenReturn(new FraseSorte("Sorte Local"));

        mockMvc.perform(get("/sorteiaFrase"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fraseSorteada").value("Sorte Local"));
    }

    @Test
    void geraImagemBiscoitoSorte_deveRetornarUrlImagem() throws Exception {
        when(openAIService.enviaImagemModel(anyString())).thenReturn("http://imagem.url");

        mockMvc.perform(get("/geraImagem")
                .param("frase", "Gato com chapéu"))
                .andExpect(status().isOk())
                .andExpect(content().string("http://imagem.url"));
    }

    @Test
    void sorteiaNumero_numeroValido_deveRetornarNumero() throws Exception {
        when(fortuneCookieService.sorteiaNumero("80")).thenReturn("80");

        mockMvc.perform(get("/sorteiaNumero/80"))
                .andExpect(status().isOk())
                .andExpect(content().string("80"));
    }

    @Test
    void sorteiaNumero_numeroInvalido_deveRetornarErro() throws Exception {
        when(fortuneCookieService.sorteiaNumero("abc")).thenThrow(new RuntimeException());

        mockMvc.perform(get("/sorteiaNumero/abc"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void iaLigada_ligandoFeature_deveRetornarMensagemLigada() throws Exception {


        mockMvc.perform(get("/ligar-ia/true"))
                .andExpect(status().isOk())
                .andExpect(content().string("Consulta via Inteligencia Artifical Ligada"));

        verify(ff4j).enable(FF4jConfig.IA_FEATURE);
    }

    @Test
    void iaLigada_desligandoFeature_deveRetornarMensagemDesligada() throws Exception {

        mockMvc.perform(get("/ligar-ia/false"))
                .andExpect(status().isOk())
                .andExpect(content().string("Consulta via base local ligada"));

        verify(ff4j).disable(FF4jConfig.IA_FEATURE);
    }
}
