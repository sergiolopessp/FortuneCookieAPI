package com.example.fortunecookie.controller;

import com.example.fortunecookie.data.Frase;
import com.example.fortunecookie.service.DataBaseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.emptyOrNullString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DataBaseController.class)
public class DataBaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DataBaseService dataBaseService;

    @Test
    void deveSalvarFraseComSucesso() throws Exception {
        Frase frase = new Frase("Beba água!");

        when(dataBaseService.salvaFrase(frase)).thenReturn(frase);

        mockMvc.perform(post("/v1/db/frase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "frase": "Beba água!"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.frase").value("Beba água!"));
    }

    @Test
    void deveBuscarFrasePorId() throws Exception {
        Frase frase = new Frase("Você terá um ótimo dia!");

        when(dataBaseService.findFraseById("1")).thenReturn(frase);

        mockMvc.perform(get("/v1/db/frase/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.frase").value("Você terá um ótimo dia!"));
    }

    @Test
    void deveSortearFraseComSucesso() throws Exception {
        Frase frase = new Frase("Sorte acompanha os ousados!");

        when(dataBaseService.sorteiaFraseDB()).thenReturn(Optional.of(frase));

        mockMvc.perform(get("/v1/db/sorteiaFrase"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.frase").value("Sorte acompanha os ousados!"));
    }

    @Test
    void deveRetornarVazioQuandoNaoHouverFrases() throws Exception {
        when(dataBaseService.sorteiaFraseDB()).thenReturn(Optional.empty());

        mockMvc.perform(get("/v1/db/sorteiaFrase"))
                .andExpect(status().isNoContent());
    }
}
