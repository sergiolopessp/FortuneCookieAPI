package com.example.fortunecookie;

import com.example.fortunecookie.controller.FortuneCookieController;
import com.example.fortunecookie.data.FraseSorte;
import com.example.fortunecookie.exceptions.NumeroNaoInformadoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FortuneCookieControllerTest {

    @Autowired
    FortuneCookieController fortuneCookieController;

    @Test
    void retornaObjetoFrase() {
        assertInstanceOf(FraseSorte.class, fortuneCookieController.sorteiaFrase());
    }

    @Test
    void retornaNumerodaSorte() {assertInstanceOf(String.class, fortuneCookieController.sorteiaNumero("80")); }

    @Test
    void retornaErroNumeroSorte() {
        Exception exception = assertThrows(NumeroNaoInformadoException.class, () -> { fortuneCookieController.sorteiaNumero("oi"); });
        assertEquals("Range de Numeros do sorteio nao informado", exception.getMessage());
    }
}
