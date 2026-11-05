package com.dam.ad.app;

import com.dam.ad.dao.LibroDao;
import com.dam.ad.model.Libro;
import java.sql.SQLException;

public class PruebaClaveForanea {
    public static void main(String[] args) {
        var dao = new LibroDao();
        int autorInexistente = 999999;

        System.out.println("=== PRUEBA NEGATIVA: autor_id inexistente (" + autorInexistente + ") ===");

        try {
            Libro libro = new Libro("Libro de prueba FK", "ISBN-PRUEBA-FK", 2000, true, autorInexistente);
            int id = dao.insert(libro);

            // Si llegamos aquí, la BD NO rechazó la operación: algo va mal
            System.out.println("ERROR: se insertó con id=" + id + " y NO debería. Se borra.");
            dao.deleteById(id);

        } catch (RuntimeException e) {
            System.out.println("Operación rechazada, como se esperaba.");
            System.out.println("Mensaje de la aplicación: " + e.getMessage());

            if (e.getCause() instanceof SQLException sql) {
                System.out.println("SQLState: " + sql.getSQLState() + " (23503 = violación de clave foránea)");
                System.out.println("Mensaje de PostgreSQL: " + sql.getMessage());
            }
        }
    }
}
