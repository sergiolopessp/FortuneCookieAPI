package com.example.fortunecookie.exceptions;

public class NumeroNaoInformadoException extends RuntimeException {

    public NumeroNaoInformadoException() {
        super("Range de Numeros do sorteio nao informado");
    }
}
