package com.dam.ad.model;

public class Libro {

    private final int id;
    private final String titulo;
    private final String isbn;
    private final Integer anio;
    private final boolean disponible;

    public Libro(int id, String titulo, String isbn, Integer anio, boolean disponible) {
        this.id = id;
        this.titulo = titulo;
        this.isbn = isbn;
        this.anio = anio;
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", isbn='" + isbn + '\'' +
                ", anio=" + anio +
                ", disponible=" + disponible +
                '}';
    }
}
