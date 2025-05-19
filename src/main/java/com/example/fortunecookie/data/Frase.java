package com.example.fortunecookie.data;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name="frases")
public class Frase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "frase")
    private String frase;

    public Frase() {}

    public Frase(String frase) {
        this.frase = frase;
    }

    public Long getId() {
        return id;
    }

    public String getFrase() {
        return frase;
    }

    public void setFrase(String frase) {
        this.frase = frase;
    }

    @Override
    public String toString() {
        return "Frase [id=" + id + ", " + frase + " ]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Frase)) return false;
        Frase frase1 = (Frase) o;
        return Objects.equals(id, frase1.id) &&
                Objects.equals(frase, frase1.frase);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, frase);
    }
}
