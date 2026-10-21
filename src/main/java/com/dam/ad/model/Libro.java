package com.dam.ad.model;

public class Libro {

    private Integer id; // ahora puede ser null antes del INSERT
    private String titulo;
    private String isbn;
    private Integer anio;
    private boolean disponible;

    // Constructor para cuando leemos de BD (id conocido)
    public Libro(Integer id, String titulo, String isbn, Integer anio, boolean disponible) {
        this.id = id;
        this.titulo = titulo;
        this.isbn = isbn;
        this.anio = anio;
        this.disponible = disponible;
    }

    // Constructor para crear un libro nuevo (id se genera en BD)
    public Libro(String titulo, String isbn, Integer anio, boolean disponible) {
        this(null, titulo, isbn, anio, disponible);
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public Integer getAnio() { return anio; }
    public void setAnio(Integer anio) { this.anio = anio; }

    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

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