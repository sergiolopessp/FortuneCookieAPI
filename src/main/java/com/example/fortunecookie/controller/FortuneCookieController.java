package com.example.fortunecookie.controller;

import com.example.fortunecookie.data.FraseSorte;

import com.example.fortunecookie.exceptions.NumeroNaoInformadoException;
import com.example.fortunecookie.service.ChatBotService;
import com.example.fortunecookie.service.FortuneCookieService;
import org.apache.hc.core5.http.ParseException;
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

    @GetMapping(value = "/sorteiaFrase", produces = MediaType.APPLICATION_JSON_VALUE)
    public FraseSorte sorteiaFrase() {
        return fortuneCookieService.sorteiaFrase();
    }

   @GetMapping(value = "/sorteiaFraseIA")
    public String sorteiaFraseIA() throws IOException, ParseException {
        return chatBotService.enviaQuery("Me de uma frase de Biscoito da Sorte");
    }
    @GetMapping("/sorteiaNumero/{numero}")
    public String sorteiaNumero(@PathVariable String numero) {
       try {
            return fortuneCookieService.sorteiaNumero(numero);
        } catch (Exception e){
            throw new NumeroNaoInformadoException();
        }

    }
}
