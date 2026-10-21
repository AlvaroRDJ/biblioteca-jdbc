package com.dam.ad.app;

import com.dam.ad.dao.LibroDao;
import com.dam.ad.model.Libro;

public class CrudDemo {
    public static void main(String[] args) {
        var dao = new LibroDao();

        System.out.println("=== ESTADO INICIAL ===");
        dao.findAll().forEach(System.out::println);

        System.out.println("\n=== INSERT ===");
        var nuevo = new Libro("Clean Code", "9780132350884-2", 2008, true);
        int id = dao.insert(nuevo);
        System.out.println("Insertado con id=" + id + " -> " + nuevo);

        System.out.println("\n=== FIND BY ID ===");
        dao.findById(id).ifPresentOrElse(
                System.out::println,
                () -> System.out.println("No encontrado")
        );

        System.out.println("\n=== UPDATE (marcar no disponible) ===");
        nuevo.setDisponible(false);
        boolean updated = dao.update(nuevo);
        System.out.println("updated=" + updated);
        System.out.println("Después del update: " + dao.findById(id).orElse(null));

        System.out.println("\n=== DELETE ===");
        boolean deleted = dao.deleteById(id);
        System.out.println("deleted=" + deleted);

        System.out.println("\n=== ESTADO FINAL ===");
        dao.findAll().forEach(System.out::println);
    }
}