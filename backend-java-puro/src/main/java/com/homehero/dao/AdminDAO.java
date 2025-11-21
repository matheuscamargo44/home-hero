package com.homehero.dao;

import com.homehero.database.DatabaseConnection;
import com.homehero.model.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {

    public Admin buscarPorEmail(String email) throws SQLException {
        String sql = "SELECT * FROM admin WHERE adm_email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Admin admin = new Admin();
                admin.setId(rs.getInt("adm_id"));
                admin.setNome(rs.getString("adm_nome"));
                admin.setEmail(rs.getString("adm_email"));
                admin.setSenha(rs.getString("adm_senha"));
                return admin;
            }
            return null;
        }
    }

    public boolean existePorEmail(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM admin WHERE adm_email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        }
    }

    public void criar(Admin admin) throws SQLException {
        String sql = "INSERT INTO admin (adm_nome, adm_email, adm_senha) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, admin.getNome());
            stmt.setString(2, admin.getEmail());
            stmt.setString(3, admin.getSenha());
            stmt.executeUpdate();
        }
    }
}

