package com.example.fortunecookie.controller;

import com.example.fortunecookie.data.Frase;
import com.example.fortunecookie.service.DataBaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/db")
public class DataBaseController {

    private final DataBaseService dataBaseService;

    public DataBaseController(DataBaseService dataBaseService) {
        this.dataBaseService = dataBaseService;
    }

    @PostMapping(path = "/frase")
    @ResponseStatus(HttpStatus.CREATED)
    public Frase salvaFrase(@RequestBody Frase frase) {
        return dataBaseService.salvaFrase(frase);
    }

    @GetMapping(path = "/frase/{id}")
    public Frase encontraFrasePorId(@PathVariable String id) {
        return dataBaseService.findFraseById(id);
    }

    @GetMapping(path = "/sorteiaFrase")
    public ResponseEntity<Frase> sorteiaFraseDb() {
        return dataBaseService.sorteiaFraseDB()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }
}
