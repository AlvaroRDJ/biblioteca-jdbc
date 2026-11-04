package com.dam.ad.model;

public class Libro {

    private Integer id;        // null antes del INSERT
    private String titulo;
    private String isbn;
    private Integer anio;
    private boolean disponible;
    private Integer autorId;   // null si el libro no tiene autor

    // Constructor completo (leer de la BD)
    public Libro(Integer id, String titulo, String isbn, Integer anio,
                 boolean disponible, Integer autorId) {
        this.id = id;
        this.titulo = titulo;
        this.isbn = isbn;
        this.anio = anio;
        this.disponible = disponible;
        this.autorId = autorId;
    }

    // Constructor de la Práctica 2 (con id, sin autor)
    public Libro(Integer id, String titulo, String isbn, Integer anio, boolean disponible) {
        this(id, titulo, isbn, anio, disponible, null);
    }

    // Constructor para libro nuevo con autor (id lo genera la BD)
    public Libro(String titulo, String isbn, Integer anio, boolean disponible, Integer autorId) {
        this(null, titulo, isbn, anio, disponible, autorId);
    }

    // Constructor de la Práctica 2 para libro nuevo (sin id y sin autor)
    public Libro(String titulo, String isbn, Integer anio, boolean disponible) {
        this(null, titulo, isbn, anio, disponible, null);
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

    public Integer getAutorId() { return autorId; }
    public void setAutorId(Integer autorId) { this.autorId = autorId; }

    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", isbn='" + isbn + '\'' +
                ", anio=" + anio +
                ", disponible=" + disponible +
                ", autorId=" + autorId +
                '}';
    }
}