package com.example.fortunecookie.service;



import com.google.gson.Gson;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatBotService {

    public static final Logger LOGGER = LoggerFactory.getLogger(ChatBotService.class);

    @Value("${chatgpt.endpoint}")
    private String endpoint;
    @Value("${chatgpt.apiKey}")
    private String apiKey;

    public String enviaQuery(String entrada) throws IOException, ParseException {

        JSONObject payload = new JSONObject();
        JSONObject mensagem = new JSONObject();
        JSONArray listaMensagem = new JSONArray();

        mensagem.put("role", "user");
        mensagem.put("content", entrada);
        listaMensagem.put(mensagem);

        payload.put("model", "gpt-3.5-turbo");
        payload.put("messages", listaMensagem);
        payload.put("temperature", 0.7);

        StringEntity inputEntity = new StringEntity(payload.toString(), ContentType.APPLICATION_JSON);

        HttpPost post = new HttpPost(this.endpoint);
        post.setEntity(inputEntity);
        post.setHeader("Authorization", "Bearer " + this.apiKey);
        post.setHeader("Content-Type", "application/json");

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse resposta = httpClient.execute(post)) {
            HttpEntity respostaEntity = resposta.getEntity();
            String respostaJsonString = EntityUtils.toString(respostaEntity, StandardCharsets.UTF_8);
            JSONObject respostaJson = new JSONObject(respostaJsonString);

            if (respostaJson.has("error")) {
                String mensagemErro = respostaJson.toString();
                LOGGER.error("Erro na chamada ao ChatGPT: {} ", mensagemErro);
                return "Error: " + mensagemErro;
            }

            JSONArray respostaArray = respostaJson.getJSONArray("choices");
            List<String> listaResposta = new ArrayList<>();

            for (int i = 0; i < respostaArray.length(); i++) {
                JSONObject objetoResposta = respostaArray.getJSONObject(i);
                String stringResposta = objetoResposta.getJSONObject("message").getString("content");
                listaResposta.add(stringResposta);
            }

            Gson gson = new Gson();
            return gson.toJson(listaResposta);
        } catch (IOException | JSONException | ParseException e) {
            LOGGER.error("Erro enviando a Request: {}", e.getMessage());
            return "Error: " + e.getMessage();
        }

    }
}
