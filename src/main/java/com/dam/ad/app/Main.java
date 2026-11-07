package com.dam.ad.app;

import com.dam.ad.repository.jdbc.JdbcLibroRepository;

public class Main {
    public static void main(String[] args) {
        var dao = new JdbcLibroRepository();
        System.out.println("=== LIBROS ===");
        dao.findAll().forEach(System.out::println);
    }
}