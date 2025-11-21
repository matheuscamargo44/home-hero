package com.homehero.dao;

import com.homehero.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EnderecoDAO {

    public Integer criar(String logradouro, String numero, String complemento, 
                        String bairro, String cidade, String uf, String cep) throws SQLException {
        String sql = "INSERT INTO endereco (end_logradouro, end_numero, end_complemento, " +
                     "end_bairro, end_cidade, end_uf, end_cep) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, logradouro);
            stmt.setString(2, numero);
            stmt.setString(3, complemento);
            stmt.setString(4, bairro);
            stmt.setString(5, cidade);
            stmt.setString(6, uf);
            stmt.setString(7, cep);
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        
        return null;
    }
}

