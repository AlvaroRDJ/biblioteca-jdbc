package com.dam.ad.model;

public class Autor {
    private Integer id; // Null antes de insertarlo en la BD.
    private String nombre;
    // Constructor para cuando leemos de la BD (id conocido).
    public Autor(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    // Constructor para crear un autor nuevo (el id lo genera la BD).
    public Autor(String nombre) {
        this(null, nombre);
    }
    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    public String getNombre() {return nombre;}
    public void setNombre() {this.nombre = nombre;}

    @Override
    public String toString() {
        return "Autor{" +
                "id=" + id +
                ", nombre= '" + nombre + '\'' +
                '}';
    }
}
