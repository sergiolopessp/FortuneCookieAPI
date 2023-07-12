package com.example.fortunecookie;

import com.example.fortunecookie.service.ChatBotService;
import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.Test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@SpringBootTest
public class ChatBotServiceTest {
    @Autowired
    private ChatBotService chatBotService;

    @Test
    void retornouAlgumaResposta() throws IOException, ParseException {
        assertInstanceOf(String.class, chatBotService.enviaQuery("teste"));
    };


}
