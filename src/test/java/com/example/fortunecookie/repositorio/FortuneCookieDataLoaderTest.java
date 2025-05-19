package com.example.fortunecookie.repositorio;

import com.example.fortunecookie.data.Frase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(properties = {"chatgpt.apiKey=valor_teste"})
@Testcontainers
public class FortuneCookieDataLoaderTest {

    @Container
    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:16.3")
            .withDatabaseName("test")
            .withUsername("root")
            .withPassword("root");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Autowired
    private FraseRepositorio fraseRepositorio;

    @Test
    void devePopularBancoSeEstiverVazio() {
        List<Frase> frases = fraseRepositorio.findAll();
        assertFalse(frases.isEmpty()); // Carga inicial foi feita
    }

    @Test
    void naoDevePopularBancoSeJaTiverFrases() throws Exception {
        long antes = fraseRepositorio.count();

        // Rodando manualmente de novo
        FortuneCookieDataLoader loader = new FortuneCookieDataLoader(fraseRepositorio);
        loader.initializer().run(null);

        long depois = fraseRepositorio.count();
        assertEquals(antes, depois); // Nada novo foi adicionado
    }
}
