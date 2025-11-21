package com.homehero.dao;

import com.homehero.database.DatabaseConnection;
import com.homehero.model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ClienteDAO {

    public Cliente buscarPorCpf(String cpf) throws SQLException {
        String sql = "SELECT * FROM cliente WHERE cli_cpf = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("cli_id"));
                cliente.setNomeCompleto(rs.getString("cli_nome"));
                cliente.setCpf(rs.getString("cli_cpf"));
                cliente.setDataNascimento(rs.getDate("cli_nascimento").toLocalDate());
                cliente.setSenha(rs.getString("cli_senha"));
                cliente.setEnderecoId(rs.getInt("end_id"));
                cliente.setEmail(rs.getString("cli_email"));
                cliente.setTelefone(rs.getString("cli_telefone"));
                return cliente;
            }
            return null;
        }
    }

    public void criar(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO cliente (cli_nome, cli_cpf, cli_nascimento, cli_senha, end_id, cli_email, cli_telefone) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cliente.getNomeCompleto());
            stmt.setString(2, cliente.getCpf());
            stmt.setDate(3, java.sql.Date.valueOf(cliente.getDataNascimento()));
            stmt.setString(4, cliente.getSenha());
            stmt.setInt(5, cliente.getEnderecoId());
            stmt.setString(6, cliente.getEmail());
            stmt.setString(7, cliente.getTelefone());
            stmt.executeUpdate();
        }
    }
}

