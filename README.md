# biblioteca-jdbc

Prácticas de **acceso a datos con JDBC** sobre **PostgreSQL**, usando el patrón **DAO**. El proyecto gestiona una tabla de libros y muestra cómo conectar Java con una base de datos relacional sin frameworks.

## Tecnologías

- Java 21
- Maven
- PostgreSQL 18
- JDBC (driver `org.postgresql:postgresql:42.7.3`)

## Estado del proyecto

- [x] **Práctica 1:** conexión JDBC y consulta `SELECT` (`findAll`)
- [ ] **Práctica 2:** CRUD completo (`findById`, `insert`, `update`, `deleteById`)

## Estructura

```
src/main/java/com/dam/ad
├── app     → clases con main (Main)
├── dao     → acceso a datos (LibroDao)
├── db      → conexión a la base de datos (Db)
└── model   → modelo de datos (Libro)
src/main/resources
├── db.properties           → configuración local (NO se sube a Git)
└── db.properties.example   → plantilla de configuración
```

## Cómo ejecutarlo

### 1. Crear la base de datos

En pgAdmin (o `psql`), ejecuta:

```sql
CREATE DATABASE biblioteca_ad;
```

Después, conectado a `biblioteca_ad`, crea la tabla y los datos de ejemplo:

```sql
CREATE TABLE IF NOT EXISTS libro (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    isbn VARCHAR(20) UNIQUE,
    anio INT,
    disponible BOOLEAN NOT NULL DEFAULT true
);

TRUNCATE TABLE libro RESTART IDENTITY;

INSERT INTO libro (titulo, isbn, anio, disponible) VALUES
('Harry Potter y la Piedra filosofal', '9780132350884', 1999, true),
('Los Pilares de la Tierra', '9780134685991', 1998, true),
('El Hobbit', '9780201633610', 1964, false);
```

### 2. Configurar la conexión

Copia `src/main/resources/db.properties.example` a `src/main/resources/db.properties` y rellena tus datos:

```properties
db.url=jdbc:postgresql://localhost:5432/biblioteca_ad
db.user=postgres
db.password=TU_CONTRASEÑA
```

> `db.properties` está en el `.gitignore` para no subir credenciales al repositorio.

### 3. Ejecutar

Abre el proyecto con IntelliJ IDEA (JDK 21), recarga Maven y ejecuta `com.dam.ad.app.Main`.

Salida esperada:

```
=== LIBROS ===
Libro{id=1, titulo='Harry Potter y la Piedra filosofal', isbn='9780132350884', anio=1999, disponible=true}
Libro{id=2, titulo='Los Pilares de la Tierra', isbn='9780134685991', anio=1998, disponible=true}
Libro{id=3, titulo='El Hobbit', isbn='9780201633610', anio=1964, disponible=false}
```

## Conceptos que practica

- Carga de configuración desde un fichero `.properties`.
- Gestión de conexiones con `DriverManager`.
- `PreparedStatement` y `ResultSet` con *try-with-resources*.
- Mapeo de filas a objetos (`Libro`), incluyendo columnas que admiten `NULL`.
- Patrón DAO para separar el acceso a datos del resto del programa.

## Autor

[AlvaroRDJ](https://github.com/AlvaroRDJ)