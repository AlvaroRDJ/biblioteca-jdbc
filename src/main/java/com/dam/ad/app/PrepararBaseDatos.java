package com.dam.ad.app;

import com.dam.ad.db.Db;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class PrepararBaseDatos {
    // Autores principales:
    private static final String[] AUTORES = {
            "J. R. R. Tolkien",
            "J. K. Rowling",
            "Ken Follett"
    };
    // Relación libro -> autor. Cada fila {ISBN del libro, nombre del autor}
    private static final String[][] RELACIONES = {
            {"9780201633610", "J. R. R. Tolkien"},
            {"9780132350884", "J. K. Rowling"},
            {"9780134685991", "Ken Follett"}
    };
    public static void main(String[] args) {
        try (Connection con = Db.getConnection()) {
            crearEsquema(con);
            insertarAutores(con);
            relacionarLibros(con);

            System.out.println();
            System.out.println("--- Preparación terminada ---");
        } catch (SQLException e) {
            System.err.println("ERROR preparando la base de datos" + e.getMessage());
            e.printStackTrace();
        }
    }
    private static void crearEsquema(Connection con) throws SQLException {
        System.out.println("1. --- ESQUEMA ---");

        String crearAutor = """
                CREATE TABLE IF NOT EXISTS autor (
                    id SERIAL PRIMARY KEY,
                    nombre VARCHAR(150) NOT NULL UNIQUE
                    )
                """;
        String anadirColumna = """
                ALTER TABLE libro
                ADD COLUMN IF NOT EXISTS autor_id INT
                CONSTRAINT fk_libro_autor REFERENCES autor(id)
                """;

        try (Statement st = con.createStatement()) {
            st.executeUpdate(crearAutor);
            System.out.println("Tabla autor: lista (creada o ya existente).");
            st.executeUpdate(anadirColumna);
            System.out.println("Columna libro.autor_id: lista (añadida o ya existente).");
        }
    }
    private static void insertarAutores(Connection con) throws SQLException {
        System.out.println();
        System.out.println("2. --- AUTORES ---");

        String sql = "INSERT INTO autor (nombre) VALUES (?) ON CONFLICT (nombre) DO NOTHING";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            for (String nombre : AUTORES) {
                ps.setString(1, nombre);
                int filas = ps.executeUpdate();
                if (filas == 1) {
                    System.out.println("Autor '" + nombre + "': insertado.");
                } else {
                    System.out.println("Autor '" + nombre + "': ya existe y no se duplica.");
                }
            }
        }
    }
    private static void relacionarLibros(Connection con) throws SQLException {
        System.out.println();
        System.out.println("3. --- RELACIONAR LIBROS POR ISBN ---");

        String sql = "UPDATE libro SET autor_id = (SELECT id FROM autor WHERE nombre = ?) WHERE isbn = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            for (String[] relacion : RELACIONES) {
                String isbn = relacion[0];
                String autor = relacion[1];

                ps.setString(1, autor);
                ps.setString(2, isbn);
                int filas = ps.executeUpdate();

                if (filas == 0) {
                    System.out.println("¡AVISO! No se encontró ningún libro con ISBN " +
                                       " (autor " + autor + "). No se actualizó nada.");
                } else {
                    System.out.println("ISBN " + isbn + " -> " + autor + ": " + filas);
                }
            }
        }
    }
}
