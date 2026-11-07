package com.dam.ad.app;

import com.dam.ad.dao.AutorDao;
import com.dam.ad.repository.jdbc.JdbcLibroRepository;
import com.dam.ad.model.Autor;
import com.dam.ad.model.Libro;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CrudDemo {
    private static final JdbcLibroRepository libroDao = new JdbcLibroRepository();
    private static final AutorDao autorDao = new AutorDao();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;

        do {
            mostrarMenu();
            opcion = leerEntero("Elige una opción: ");

            try {
                switch (opcion) {
                    case 1 -> listarLibros();
                    case 2 -> anadirLibro();
                    case 3 -> cambiarDisponibilidad();
                    case 4 -> borrarLibro();
                    case 5 -> consultarPorAutor();
                    case 0 -> System.out.println("Saliendo. ¡Hasta luego!");
                    default -> System.out.println("Opción no válida.");
                }
            } catch (RuntimeException e) {
                // Un error de BD no debe cerrar el menú
                System.out.println("ERROR: " + e.getMessage());
                if (e.getCause() != null) {
                    System.out.println("Causa: " + e.getCause().getMessage());
                }
            }
        } while (opcion != 0);
    }
    // ---------- Menú ----------
    private static void mostrarMenu() {
        System.out.println();
        System.out.println("------ MENÚ BIBLIOTECA ------");
        System.out.println("1. Listar libros");
        System.out.println("2. Añadir libro");
        System.out.println("3. Cambiar disponibilidad de un libro");
        System.out.println("4. Borrar libro");
        System.out.println("5. Consultar libros por autor");
        System.out.println("0. Salir");
        System.out.println("-----------------------------");
    }
    // ---------- Operaciones ----------
    private static void listarLibros() {
        System.out.println("--- LIBROS ---");
        List<Libro> libros = libroDao.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros.");
            return;
        }
        for (Libro libro : libros) {
            System.out.println(describir(libro));
        }
    }
    private static void anadirLibro() {
        System.out.println("--- AÑADIR LIBRO ---");
        String titulo = leerTexto("Título: ");
        String isbn = leerTexto("ISBN: ");
        int anio = leerEntero("Año: ");

        listarAutores();
        int autorId = leerEntero("Id del autor (0 = sin autor): ");
        Integer autorFk = (autorId == 0) ? null : autorId;

        Libro nuevo = new Libro(titulo, isbn, anio, true, autorFk);
        int id = libroDao.insert(nuevo);
        System.out.println("Libro insertado con id=" + id + " -> " + nuevo);
    }
    private static void cambiarDisponibilidad() {
        System.out.println("--- CAMBIAR DISPONIBILIDAD ---");
        int id = leerEntero("Id del libro: ");

        Optional<Libro> encontrado = libroDao.findById(id);
        if (encontrado.isEmpty()) {
            System.out.println("No existe ningún libro con id=" + id);
            return;
        }
        Libro libro = encontrado.get();
        boolean anterior = libro.isDisponible();
        libro.setDisponible(!anterior);

        boolean actualizado = libroDao.update(libro);
        if (actualizado) {
            System.out.println("Actualizado (1 fila afectada). disponible: "
                    + anterior + " -> " + libro.isDisponible());
            System.out.println(describir(libroDao.findById(id).orElse(libro)));
        } else {
            System.out.println("No se actualizó ninguna fila.");
        }
    }
    private static void borrarLibro() {
        System.out.println("--- BORRAR LIBRO ---");
        int id = leerEntero("Id del libro: ");

        boolean borrado = libroDao.deleteById(id);
        if (borrado) {
            System.out.println("Libro id=" + id + " borrado (1 fila afectada).");
        } else {
            System.out.println("No se borró nada: no existe ningún libro con id=" + id);
        }
    }
    private static void consultarPorAutor() {
        System.out.println("--- LIBROS POR AUTOR ---");
        listarAutores();
        int autorId = leerEntero("Id del autor: ");

        Optional<Autor> autor = autorDao.findById(autorId);
        if (autor.isEmpty()) {
            System.out.println("No existe ningún autor con id=" + autorId);
            return;
        }
        List<Libro> libros = libroDao.findByAutorId(autorId);
        System.out.println("Libros de " + autor.get().getNombre() + ":");
        if (libros.isEmpty()) {
            System.out.println("  (este autor no tiene libros)");
        } else {
            for (Libro libro : libros) {
                System.out.println("  " + libro);
            }
        }
    }
    // ---------- Ayudas ----------
    private static void listarAutores() {
        System.out.println("Autores disponibles:");
        for (Autor autor : autorDao.findAll()) {
            System.out.println("  " + autor.getId() + ") " + autor.getNombre());
        }
    }
    private static String describir(Libro libro) {
        String nombreAutor;
        if (libro.getAutorId() == null) {
            nombreAutor = "sin autor";
        } else {
            nombreAutor = autorDao.findById(libro.getAutorId())
                    .map(Autor::getNombre)
                    .orElse("autor desconocido");
        }
        return libro + "  [autor: " + nombreAutor + "]";
    }
    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = sc.nextLine().trim();
            try {
                return Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                System.out.println("Introduce un número entero válido.");
            }
        }
    }
    private static String leerTexto(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = sc.nextLine().trim();
            if (!linea.isEmpty()) {
                return linea;
            }
            System.out.println("Este campo no puede estar vacío.");
        }
    }
}