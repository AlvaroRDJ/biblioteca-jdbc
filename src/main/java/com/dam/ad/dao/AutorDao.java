package com.dam.ad.dao;

import com.dam.ad.db.Db;
import com.dam.ad.model.Autor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AutorDao {
    private Autor mapRow(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nombre = rs.getString("nombre");
        return new Autor(id, nombre);
    }
    public List<Autor> findAll() {
        String sql = "SELECT id, nombre FROM autor ORDER BY id";

        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<Autor> autores = new ArrayList<>();
            while (rs.next()) {
                autores.add(mapRow(rs));
            }
            return autores;
        } catch (SQLException e) {
            throw new RuntimeException("Error leyendo autores", e);
        }
    }
    public Optional<Autor> findById(int id) {
        String sql = "SELECT id, nombre FROM autor WHERE id = ?";

        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
             ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error buscando autor por id=" + id, e);
        }
    }
    public int insert(Autor autor) {
        String sql = "INSERT INTO autor (nombre) VALUES (?)";

        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, autor.getNombre());

            int rows = ps.executeUpdate();
            if (rows != 1) {
                throw new SQLException("INSERT inesperado. Filas afectadas: " + rows);
            }
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    int id = keys.getInt(1);
                    autor.setId(id);
                    return id;
                }
                throw new SQLException("No se devolvió id generado (getGeneratedKeys vacío)");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error insertando autor", e);
        }
    }
}