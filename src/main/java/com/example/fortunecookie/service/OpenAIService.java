package com.example.fortunecookie.service;


import dev.langchain4j.data.image.Image;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.image.ImageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiImageModel;
import dev.langchain4j.model.output.Response;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class OpenAIService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenAIService.class);

    private final DistributionSummary summary;

    public OpenAIService(MeterRegistry registro) {
        this.summary = DistributionSummary.builder("Dados Sumarizados")
                .description("Uma metrica customizada")
                .tags("Valor", "Trafegado")
                .register(registro);
    }

    @Value("${chatgpt.apiKey}")
    private String apiKey;
    public String enviaQuery(String entrada)  {

        ChatLanguageModel model =  OpenAiChatModel.withApiKey(apiKey);
        this.summary.record(1L);
        return model.generate(entrada);
    }

    public String enviaQueryModel(String entrada) {

        ChatLanguageModel model = OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName("gpt-3.5-turbo")
                .temperature(0.3)
                .timeout(Duration.ofSeconds(60))
                .logRequests(true)
                .logResponses(true)
                .build();

        return model.generate(entrada);
    }

    public String enviaImagemModel(String entrada) {

        ImageModel model = OpenAiImageModel.builder()
                .modelName("dall-e-3")
                .apiKey(apiKey)
                .build();


        Response<Image> resposta = model.generate(entrada);

        return resposta.content().url().toString();
    }


}
