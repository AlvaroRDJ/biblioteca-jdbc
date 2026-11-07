package com.dam.ad.repository;

import com.dam.ad.model.Libro;
import java.util.List;
import java.util.Optional;

public interface LibroRepository {

    List<Libro> findAll();

    Optional<Libro> findById(int id);

    List<Libro> findByAutorId(int autorId);

    int insert(Libro libro);

    boolean update(Libro libro);

    boolean deleteById(int id);
}
