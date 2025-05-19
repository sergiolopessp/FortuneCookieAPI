package com.example.fortunecookie.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NumeroNaoInformadoExceptionTest {

    @Test
    void deveConterMensagemPadrao() {
        NumeroNaoInformadoException ex = new NumeroNaoInformadoException();

        assertEquals("Range de Numeros do sorteio nao informado", ex.getMessage());
    }

    @Test
    void deveSerRuntimeException() {
        NumeroNaoInformadoException ex = new NumeroNaoInformadoException();

        assertTrue(ex instanceof RuntimeException);
    }
}
