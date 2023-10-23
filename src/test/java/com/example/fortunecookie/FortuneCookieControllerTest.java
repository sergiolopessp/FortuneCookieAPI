package com.example.fortunecookie;

import com.example.fortunecookie.controller.FortuneCookieController;
import com.example.fortunecookie.data.FraseSorte;
import com.example.fortunecookie.exceptions.NumeroNaoInformadoException;

import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@SpringBootTest
class FortuneCookieControllerTest {

    @Autowired
    FortuneCookieController fortuneCookieController;

    @Test
    void retornaObjetoFrase() throws IOException, ParseException {
        assertInstanceOf(FraseSorte.class, fortuneCookieController.sorteiaFrase());
    }

    @Test
    void retornaNumerodaSorte() {assertInstanceOf(String.class, fortuneCookieController.sorteiaNumero("80")); }

    @Test
    void retornaFraseIA() throws IOException, ParseException {
        assertInstanceOf(String.class, fortuneCookieController.sorteiaFraseIA());
    }

    @Test
    void retornaErroNumeroSorte() {
        Exception exception = assertThrows(NumeroNaoInformadoException.class, () -> { fortuneCookieController.sorteiaNumero("oi"); });
        assertEquals("Range de Numeros do sorteio nao informado", exception.getMessage());
    }
}
