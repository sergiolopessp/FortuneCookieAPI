package com.example.fortunecookie.dto;

import java.io.Serializable;

public class FraseSorte implements Serializable {

    String fraseSorteada;

    public FraseSorte() {
    }

    public FraseSorte(String fraseSorteada) {
        this.fraseSorteada = fraseSorteada;
    }

    public String getFraseSorteada() {
        return fraseSorteada;
    }

    public void setFraseSorteada(String fraseSorteada) {
        this.fraseSorteada = fraseSorteada;
    }
}
