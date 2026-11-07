package com.dam.ad.service;

import com.dam.ad.model.Libro;
import com.dam.ad.repository.LibroRepository;
import java.util.List;

public class LibroService {
    private final LibroRepository repo; // dependencia
    public LibroService(LibroRepository repo) { // inyección por constructor
        this.repo = repo;
    }
    public List<Libro> listar() {
        return repo.findAll();
    }
    public Libro verDetalle(int id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe libro con id=" + id));
    }
    public int alta(Libro libro) {
        if (libro.getTitulo() == null || libro.getTitulo().isBlank())
            throw new IllegalArgumentException("Título obligatorio");
        if (libro.getIsbn() == null || libro.getIsbn().isBlank())
            throw new IllegalArgumentException("ISBN obligatorio");
        return repo.insert(libro);
    }
    public void marcarNoDisponible(int id) {
        Libro l = verDetalle(id);
        l.setDisponible(false);
        boolean ok = repo.update(l);
        if (!ok) throw new IllegalStateException("No se pudo actualizar id=" + id);
    }
    public void borrar(int id) {
        boolean ok = repo.deleteById(id);
        if (!ok) throw new IllegalArgumentException("No existe id=" + id);
    }
}