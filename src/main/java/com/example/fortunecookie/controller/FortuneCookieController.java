package com.example.fortunecookie.controller;

import com.example.fortunecookie.data.FraseSorte;

import com.example.fortunecookie.exceptions.NumeroNaoInformadoException;
import com.example.fortunecookie.service.FortuneCookieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FortuneCookieController {

    @Autowired
    FortuneCookieService fortuneCookieService;

    @GetMapping(value = "/sorteiaFrase", produces = MediaType.APPLICATION_JSON_VALUE)
    public FraseSorte sorteiaFrase() {
        return fortuneCookieService.sorteiaFrase();
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
