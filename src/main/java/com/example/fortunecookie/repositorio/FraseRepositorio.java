package com.example.fortunecookie.repositorio;

import com.example.fortunecookie.data.Frase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FraseRepositorio extends JpaRepository<Frase, Long> {

    Optional<Frase> findByFrase(String frase);
}
