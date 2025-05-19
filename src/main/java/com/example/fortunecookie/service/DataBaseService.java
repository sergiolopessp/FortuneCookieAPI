package com.example.fortunecookie.service;

import com.example.fortunecookie.data.Frase;
import com.example.fortunecookie.repositorio.FraseRepositorio;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@Service
public class DataBaseService {

    private final FraseRepositorio fraseRepositorio;

    public DataBaseService(FraseRepositorio fraseRepositorio) {
        this.fraseRepositorio = fraseRepositorio;
    }

    public Frase salvaFrase(Frase frase) {
        return fraseRepositorio.save(frase);
    }

    public Frase findFraseById(String id) {
        return fraseRepositorio.findById(Long.valueOf(id)).get();
    }

    public Optional<Frase> sorteiaFraseDB() {
        List<Frase> frases = fraseRepositorio.findAll();

        SecureRandom random = new SecureRandom();

        int indice = random.nextInt(frases.size());

        return frases.stream()
                .skip(indice)
                .findFirst();
    }
}
