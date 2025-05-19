package com.example.fortunecookie.service;

import com.example.fortunecookie.data.FortuneCookieData;
import com.example.fortunecookie.dto.FraseSorte;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class FortuneCookieService {


    private final List<String> frases = FortuneCookieData.getFrases();

    private final AtomicInteger meuMedidor = new AtomicInteger();

    public FortuneCookieService(MeterRegistry registro) {
        Gauge.builder("Meu.Medidor", meuMedidor, AtomicInteger::get)
                .description("Mede alguma coisa")
                .tags("Tamanho", "da Lista")
                .register(registro);
    }

    public FraseSorte sorteiaFrase() {

        SecureRandom random = new SecureRandom();
        int indice = random.nextInt(frases.size());
        meuMedidor.set(indice);
        return new FraseSorte(frases.get(indice));

    }


    public String sorteiaNumero(String numero) {
        SecureRandom random = new SecureRandom();
        StringBuilder numerodaSorte = new StringBuilder();
        numerodaSorte.append("Seu número da sorte é : ");
        numerodaSorte.append(random.nextInt(Integer.parseInt(numero)));
        return numerodaSorte.toString();
    }

}
