package com.example.fortunecookie.repositorio;

import com.example.fortunecookie.data.Frase;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FraseRepositorioTest {

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

    @BeforeAll
    static void beforeAll(){
        postgreSQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }

    @Test
    @Order(1)
    public void deveCriarUmaFraseComFrasePassada() {
        Frase frase = criaFrase();
       fraseRepositorio.save(frase);

        Optional<Frase> fraseObtida = fraseRepositorio.findByFrase(frase.getFrase());

        Assertions.assertThat(fraseObtida.get().getFrase()).isEqualTo(frase.getFrase());

    }

    @Test
    @Order(2)
    public void deveRetornarUmaListadeFrases() {
        Frase frase = criaFrase();

        fraseRepositorio.save(frase);

        List<Frase> resultado = fraseRepositorio.findAll();

        Assertions.assertThat(resultado)
                .isNotNull()
                .hasSize(1);

    }



    private Frase criaFrase() {
        var frase = "Fazer ou Não Fazer. Não Existe Tentar";
        return new Frase(frase);
    }
}
