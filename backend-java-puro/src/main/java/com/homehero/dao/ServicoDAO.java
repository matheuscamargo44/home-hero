package com.homehero.dao;

import com.homehero.database.DatabaseConnection;
import com.homehero.model.Servico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicoDAO {

    public List<Servico> listarAtivos() throws SQLException {
        String sql = "SELECT * FROM servico WHERE ser_ativo = true";
        List<Servico> servicos = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Servico servico = new Servico();
                servico.setId(rs.getInt("ser_id"));
                servico.setNome(rs.getString("ser_nome"));
                servico.setDescricao(rs.getString("ser_descricao"));
                servico.setPrecoBase(rs.getFloat("ser_preco_base"));
                servico.setAtivo(rs.getBoolean("ser_ativo"));
                servico.setCategoriaId(rs.getInt("cat_id"));
                servicos.add(servico);
            }
        }
        
        return servicos;
    }
}

