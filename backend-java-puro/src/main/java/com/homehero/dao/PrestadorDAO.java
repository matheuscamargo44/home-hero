package com.homehero.dao;

import com.homehero.database.DatabaseConnection;
import com.homehero.model.Prestador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PrestadorDAO {

    public Prestador buscarPorCpf(String cpf) throws SQLException {
        String sql = "SELECT * FROM prestador WHERE pre_cpf = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Prestador prestador = new Prestador();
                prestador.setId(rs.getInt("pre_id"));
                prestador.setNome(rs.getString("pre_nome"));
                prestador.setCpf(rs.getString("pre_cpf"));
                prestador.setNascimento(rs.getDate("pre_nascimento").toLocalDate());
                prestador.setAreas(rs.getString("pre_areas"));
                prestador.setExperiencia(rs.getString("pre_experiencia"));
                prestador.setCertificados(rs.getString("pre_certificados"));
                prestador.setSenha(rs.getString("pre_senha"));
                prestador.setEnderecoId(rs.getInt("end_id"));
                prestador.setEmail(rs.getString("pre_email"));
                prestador.setTelefone(rs.getString("pre_telefone"));
                return prestador;
            }
            return null;
        }
    }

    public void criar(Prestador prestador) throws SQLException {
        String sql = "INSERT INTO prestador (pre_nome, pre_cpf, pre_nascimento, pre_areas, pre_experiencia, " +
                     "pre_certificados, pre_senha, end_id, pre_email, pre_telefone) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, prestador.getNome());
            stmt.setString(2, prestador.getCpf());
            stmt.setDate(3, java.sql.Date.valueOf(prestador.getNascimento()));
            stmt.setString(4, prestador.getAreas());
            stmt.setString(5, prestador.getExperiencia());
            stmt.setString(6, prestador.getCertificados());
            stmt.setString(7, prestador.getSenha());
            stmt.setInt(8, prestador.getEnderecoId());
            stmt.setString(9, prestador.getEmail());
            stmt.setString(10, prestador.getTelefone());
            stmt.executeUpdate();
        }
    }
}

