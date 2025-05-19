package com.example.fortunecookie.service;

import com.example.fortunecookie.data.Frase;
import com.example.fortunecookie.repositorio.FraseRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class DataBaseServiceTest {

    private FraseRepositorio fraseRepositorio;
    private DataBaseService dataBaseService;

    @BeforeEach
    void setUp() {
        fraseRepositorio = mock(FraseRepositorio.class);
        dataBaseService = new DataBaseService(fraseRepositorio);
    }

    @Test
    void deveSalvarFraseComSucesso() {
        Frase frase = new Frase("Teste");

        when(fraseRepositorio.save(frase)).thenReturn(frase);

        Frase resultado = dataBaseService.salvaFrase(frase);

        assertEquals(frase, resultado);
        verify(fraseRepositorio).save(frase);
    }

    @Test
    void deveBuscarFrasePorId() {
        Frase frase = new Frase( "Olá");
        when(fraseRepositorio.findById(1L)).thenReturn(Optional.of(frase));

        Frase resultado = dataBaseService.findFraseById("1");

        assertEquals("Olá", resultado.getFrase());
        verify(fraseRepositorio).findById(1L);
    }

    @Test
    void deveSortearFraseDoBanco() {
        Frase frase1 = new Frase("A vida é bela");
        Frase frase2 = new Frase("Siga em frente");
        List<Frase> frases = Arrays.asList(frase1, frase2);

        when(fraseRepositorio.findAll()).thenReturn(frases);

        Optional<Frase> sorteada = dataBaseService.sorteiaFraseDB();

        assertTrue(sorteada.isPresent());
        assertTrue(frases.contains(sorteada.get()));
        verify(fraseRepositorio).findAll();
    }
}
