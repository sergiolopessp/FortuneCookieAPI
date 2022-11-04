package com.example.fortunecookie;

import com.example.fortunecookie.data.FraseSorte;
import com.example.fortunecookie.service.FortuneCookieService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@SpringBootTest
class FortuneCookieServiceTest {

    @Autowired
    FortuneCookieService fortuneCookieService;

    @Test
    void sorteiaFraseSucesso() {
        assertInstanceOf(FraseSorte.class, fortuneCookieService.sorteiaFrase());
    }
}
