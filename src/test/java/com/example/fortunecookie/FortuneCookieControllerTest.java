package com.example.fortunecookie;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import com.example.fortunecookie.controller.FortuneCookieController;
import com.example.fortunecookie.data.FraseSorte;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FortuneCookieControllerTest {

    @Autowired
    FortuneCookieController fortuneCookieController;

    @Test
    void retornaObjetoFrase() {
        assertInstanceOf(FraseSorte.class, fortuneCookieController.sorteiaFrase());
    }
}
