package com.example.fortunecookie.service;

import com.example.fortunecookie.repositorio.FraseRepositorio;
import dev.langchain4j.data.image.Image;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.image.ImageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiImageModel;
import dev.langchain4j.model.output.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.lang.reflect.Field;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(properties = {"chatgpt.apiKey=valor_teste"})
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class OpenAIServiceTest {

    @Autowired
    OpenAIService openAIService;

    @MockitoBean
    private FraseRepositorio fraseRepositorio;

    private final String apiKey = "test-key";

    @BeforeEach
    void configurarValorManualmente() throws Exception {
        // Força a injeção do @Value manualmente se Spring falhar
        Field field = OpenAIService.class.getDeclaredField("apiKey");
        field.setAccessible(true);
        field.set(openAIService, apiKey);
    }

    @Test
    void deveGerarTextoComEnvioSimples() {
        String entrada = "Olá, IA!";
        String respostaEsperada = "Resposta Simples";

        try (MockedStatic<OpenAiChatModel> mocked = mockStatic(OpenAiChatModel.class)) {
            OpenAiChatModel modelMock = mock(OpenAiChatModel.class);
            when(modelMock.generate(entrada)).thenReturn(respostaEsperada);
            mocked.when(() -> OpenAiChatModel.withApiKey(apiKey)).thenReturn(modelMock);

            String resposta = openAIService.enviaQuery(entrada);

            assertEquals(respostaEsperada, resposta);
        }
    }

    @Test
    void deveGerarImagemComUrl() throws Exception {
        String entrada = "Gato com chapéu";
        String urlEsperada = "https://imagem-exemplo.com";

        try (MockedStatic<OpenAiImageModel> mockedStatic = mockStatic(OpenAiImageModel.class)) {
            // Mock do modelo
            OpenAiImageModel modelMock = mock(OpenAiImageModel.class);
            Response<Image> responseMock = mock(Response.class);
            Image imageMock = mock(Image.class);

            // Configuração do comportamento do mock
            when(imageMock.url()).thenReturn(new URL(urlEsperada).toURI());
            when(responseMock.content()).thenReturn(imageMock);
            when(modelMock.generate(entrada)).thenReturn(responseMock);

            // Simula a chamada do builder().build() retornando o mock
            mockedStatic.when(() -> OpenAiImageModel.withApiKey("test-key"))
                    .thenReturn(modelMock);

            // Executa o método real
            String resultado = openAIService.enviaImagemModel(entrada);

            // Verifica
            assertEquals(urlEsperada, resultado);
        }
    }
}
