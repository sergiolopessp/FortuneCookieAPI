package com.example.fortunecookie.controller;

import com.example.fortunecookie.configuration.FF4jConfig;
import com.example.fortunecookie.data.FraseSorte;

import com.example.fortunecookie.exceptions.NumeroNaoInformadoException;
import com.example.fortunecookie.service.ChatBotService;
import com.example.fortunecookie.service.FortuneCookieService;
import com.example.fortunecookie.service.OpenAIService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.apache.hc.core5.http.ParseException;
import org.ff4j.FF4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RestController;


import java.io.IOException;



@RestController
public class FortuneCookieController {

    @Autowired
    FortuneCookieService fortuneCookieService;

    @Autowired
    ChatBotService chatBotService;

    @Autowired
    OpenAIService openAIService;

    @Autowired
    FF4j ff4j;

    private final Counter meuContador;
    private final Timer meuTimer;

    public FortuneCookieController(MeterRegistry registro) {
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
            return new FraseSorte(chatBotService.enviaQuery("Me de uma frase de Biscoito da Sorte"));
        } else {
            this.meuContador.increment();
            return fortuneCookieService.sorteiaFrase();
        }


    }

   @GetMapping(value = "/sorteiaFraseIA")
    public String sorteiaFraseIA() throws IOException, ParseException {
        return this.meuTimer.record(() -> {
            try {
                return chatBotService.enviaQuery("Me de uma frase de Biscoito da Sorte");
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
   }

    @GetMapping(value = "/sorteiaFraseOpenAi")
    public String sorteiaFraseOpenAi()  {
        return openAIService.enviaQueryModel("Me de uma frase de Biscoito da Sorte");
    }


    @GetMapping("/geraImagem")
    public String geraImagemBiscoitoSorte() {
        //return openAIService.enviaImagemModel("Construa uma imagem com um pessoa com barba vermelha com um biscoito da sorte");
        return openAIService.enviaImagemModel("WebAuthn/FIDO2: Demystifying attestation and MDS");
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
