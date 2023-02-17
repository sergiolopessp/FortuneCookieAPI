package com.example.fortunecookie.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NumeroNaoInformadoException.class)
    ProblemDetail handleNumeroNaoInformadoException(NumeroNaoInformadoException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        problemDetail.setTitle("Numero nao informado para o sorteiro");
        problemDetail.setDetail("É preciso informar um numero de base para o sorteio. Ex : /sorteiaNumero/80");
        problemDetail.setProperty("Categoria", "Plataforma");
        problemDetail.setProperty("TimeStamp", Instant.now());
        problemDetail.setProperty("StackTrace", e.getStackTrace());
        return problemDetail;
    }
}
