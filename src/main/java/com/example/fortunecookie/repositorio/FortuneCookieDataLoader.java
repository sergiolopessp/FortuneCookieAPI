package com.example.fortunecookie.repositorio;

import com.example.fortunecookie.data.FortuneCookieData;
import com.example.fortunecookie.data.Frase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FortuneCookieDataLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(FortuneCookieDataLoader.class);

    private final FraseRepositorio fraseRepositorio;


    public FortuneCookieDataLoader(FraseRepositorio fraseRepositorio) {
        this.fraseRepositorio = fraseRepositorio;
    }

    @Bean
    public ApplicationRunner initializer() {
        return args -> {
            List<String> frases = FortuneCookieData.getFrases();
            try {
                if (fraseRepositorio.count() == 0) {
                    frases.forEach(frase -> fraseRepositorio.save(new Frase(frase)));
                    LOGGER.info("Carga Inicial do Banco");
                } else {
                    LOGGER.info("O banco ja estã alimentado");
                }
            } catch (DataAccessException e) {
                LOGGER.error("Erro ao acessar o banco de dados. A aplicação continua subindo mesmo assim.");
            }
        };
    }
}
