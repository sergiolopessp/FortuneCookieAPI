package com.example.fortunecookie.controller;

import com.example.fortunecookie.configuration.FF4jConfig;
import com.example.fortunecookie.dto.FraseSorte;

import com.example.fortunecookie.exceptions.NumeroNaoInformadoException;
import com.example.fortunecookie.service.FortuneCookieService;
import com.example.fortunecookie.service.OpenAIService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.apache.hc.core5.http.ParseException;
import org.ff4j.FF4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.io.IOException;



@RestController
public class FortuneCookieController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FortuneCookieController.class);

    private final FortuneCookieService fortuneCookieService;

    private final OpenAIService openAIService;

    private final FF4j ff4j;

    private final Counter meuContador;
    private final Timer meuTimer;



    public FortuneCookieController(FortuneCookieService fortuneCookieService,
                                   OpenAIService openAIService,
                                   FF4j ff4j,
                                   MeterRegistry registro) {
        this.fortuneCookieService = fortuneCookieService;
        this.openAIService = openAIService;
        this.ff4j = ff4j;

        this.meuContador = Counter.builder("Meu.Contador")
                .description("Quantas Chamadas ao Método")
                .tags("AcessoLocal", "ListaInterna")
                .register(registro);

        this.meuTimer = Timer.builder("Meu.Temporizador")
                .description("Tempo de Execução do Método")
                .tags("ChatGPT", "ChamadaIA")
                .register(registro);

    }

    @GetMapping(value = "/sorteiaFrase", produces = MediaType.APPLICATION_JSON_VALUE)
    public FraseSorte sorteiaFrase() throws IOException, ParseException {
        if (ff4j.check(FF4jConfig.IA_FEATURE)) {
            return new FraseSorte(openAIService.enviaQueryModel("Me de uma frase de Biscoito da Sorte"));
        } else {
            this.meuContador.increment();
            return fortuneCookieService.sorteiaFrase();
        }


    }

    @GetMapping(value = "/sorteiaFraseOpenAi")
    public String sorteiaFraseOpenAi()  {
        return openAIService.enviaQueryModel("Me de uma frase de Biscoito da Sorte");
    }


    @GetMapping("/geraImagem")
    public String geraImagemBiscoitoSorte(@RequestParam String frase) {
        LOGGER.info("Frase de entrada:" + frase);
        return openAIService.enviaImagemModel(frase);
    }

    @GetMapping("/sorteiaNumero/{numero}")
    public String sorteiaNumero(@PathVariable String numero) {
       try {
            return fortuneCookieService.sorteiaNumero(numero);
        } catch (Exception e){
            throw new NumeroNaoInformadoException();
        }

    }

    @GetMapping(path = "/ligar-ia/{ligarIA}")
    public String iaLigada(@PathVariable boolean ligarIA) {
        if (ligarIA) {
            ff4j.enable(FF4jConfig.IA_FEATURE);
            return "Consulta via Inteligencia Artifical Ligada";
        } else {
            ff4j.disable(FF4jConfig.IA_FEATURE);
            return "Consulta via base local ligada";
        }
        
    }

}
