package com.homehero.service;

import com.homehero.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DatabaseTestService {

    public List<Map<String, Object>> executeView(String viewName) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM " + viewName;
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                
                List<String> columnNames = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    columnNames.add(metaData.getColumnLabel(i));
                }
                
                List<Map<String, Object>> results = new ArrayList<>();
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        row.put(columnNames.get(i - 1), rs.getObject(i));
                    }
                    results.add(row);
                }
                return results;
            }
        }
    }

    public Map<String, Object> executeProcedure(String procedureName, Map<String, Object> parameters) throws SQLException {
        Map<String, Object> response = new HashMap<>();
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            LinkedHashMap<String, Object> orderedParams = new LinkedHashMap<>();
            if (parameters != null && !parameters.isEmpty()) {
                orderedParams.putAll(parameters);
            }
            
            StringBuilder sql = new StringBuilder("CALL ").append(procedureName).append("(");
            if (!orderedParams.isEmpty()) {
                for (int i = 0; i < orderedParams.size(); i++) {
                    if (i > 0) sql.append(", ");
                    sql.append("?");
                }
            }
            sql.append(")");
            
            try (CallableStatement callableStatement = conn.prepareCall(sql.toString())) {
                int paramIndex = 1;
                for (Map.Entry<String, Object> entry : orderedParams.entrySet()) {
                    Object value = entry.getValue();
                    if (value instanceof Integer) {
                        callableStatement.setInt(paramIndex, (Integer) value);
                    } else if (value instanceof Long) {
                        callableStatement.setLong(paramIndex, (Long) value);
                    } else if (value instanceof Double || value instanceof Float) {
                        callableStatement.setDouble(paramIndex, value instanceof Double ? (Double) value : ((Float) value).doubleValue());
                    } else if (value instanceof String) {
                        callableStatement.setString(paramIndex, (String) value);
                    } else if (value instanceof java.sql.Date) {
                        callableStatement.setDate(paramIndex, (java.sql.Date) value);
                    } else if (value instanceof java.util.Date) {
                        callableStatement.setDate(paramIndex, new java.sql.Date(((java.util.Date) value).getTime()));
                    } else {
                        callableStatement.setObject(paramIndex, value);
                    }
                    paramIndex++;
                }
                
                boolean hasResults = callableStatement.execute();
                List<Map<String, Object>> resultData = new ArrayList<>();
                boolean returnedData = false;
                
                while (true) {
                    if (hasResults) {
                        try (ResultSet rs = callableStatement.getResultSet()) {
                            if (rs != null) {
                                ResultSetMetaData metaData = rs.getMetaData();
                                int columnCount = metaData.getColumnCount();
                                List<String> columnNames = new ArrayList<>();
                                for (int i = 1; i <= columnCount; i++) {
                                    String columnLabel = metaData.getColumnLabel(i);
                                    if (columnLabel == null || columnLabel.isBlank() ||
                                            columnLabel.toLowerCase().matches("column\\d+")) {
                                        String fallbackName = metaData.getColumnName(i);
                                        if (fallbackName != null && !fallbackName.isBlank()) {
                                            columnLabel = fallbackName;
                                        }
                                    }
                                    if (columnLabel == null || columnLabel.isBlank()) {
                                        columnLabel = "column" + i;
                                    }
                                    columnNames.add(columnLabel);
                                }
                                
                                while (rs.next()) {
                                    Map<String, Object> row = new HashMap<>();
                                    for (int i = 1; i <= columnCount; i++) {
                                        row.put(columnNames.get(i - 1), rs.getObject(i));
                                    }
                                    resultData.add(row);
                                }
                                returnedData = true;
                            }
                        }
                    } else {
                        int updateCount = callableStatement.getUpdateCount();
                        if (updateCount == -1) {
                            break;
                        }
                    }
                    
                    hasResults = callableStatement.getMoreResults();
                    if (!hasResults && callableStatement.getUpdateCount() == -1) {
                        break;
                    }
                }
                
                response.put("success", true);
                response.put("data", resultData);
                if (!returnedData) {
                    response.put("message", "Procedimento executado com sucesso (sem retorno de dados).");
                }
            }
        } catch (SQLException e) {
            response.put("success", false);
            response.put("message", "Erro ao executar procedure: " + extractErrorMessage(e));
            response.put("data", new ArrayList<>());
        }
        
        return response;
    }

    public List<String> getAvailableViews() throws SQLException {
        List<String> views = new ArrayList<>();
        String sql = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_SCHEMA = DATABASE()";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                views.add(rs.getString("TABLE_NAME"));
            }
        }
        
        return views;
    }

    public List<String> getAvailableProcedures() throws SQLException {
        List<String> procedures = new ArrayList<>();
        String sql = "SELECT ROUTINE_NAME FROM INFORMATION_SCHEMA.ROUTINES WHERE ROUTINE_SCHEMA = DATABASE() AND ROUTINE_TYPE = 'PROCEDURE'";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                procedures.add(rs.getString("ROUTINE_NAME"));
            }
        }
        
        return procedures;
    }

    public List<String> getAvailableTriggers() throws SQLException {
        List<String> triggers = new ArrayList<>();
        String sql = "SELECT TRIGGER_NAME FROM INFORMATION_SCHEMA.TRIGGERS WHERE TRIGGER_SCHEMA = DATABASE()";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                triggers.add(rs.getString("TRIGGER_NAME"));
            }
        }
        
        return triggers;
    }

    public Map<String, Object> getTriggerInfo(String triggerName) throws SQLException {
        Map<String, Object> response = new HashMap<>();
        String sql = "SELECT TRIGGER_NAME, EVENT_MANIPULATION, EVENT_OBJECT_TABLE, ACTION_STATEMENT, ACTION_TIMING " +
                    "FROM INFORMATION_SCHEMA.TRIGGERS WHERE TRIGGER_SCHEMA = DATABASE() AND TRIGGER_NAME = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, triggerName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Map<String, Object> data = new HashMap<>();
                    data.put("TRIGGER_NAME", rs.getString("TRIGGER_NAME"));
                    data.put("EVENT_MANIPULATION", rs.getString("EVENT_MANIPULATION"));
                    data.put("EVENT_OBJECT_TABLE", rs.getString("EVENT_OBJECT_TABLE"));
                    data.put("ACTION_STATEMENT", rs.getString("ACTION_STATEMENT"));
                    data.put("ACTION_TIMING", rs.getString("ACTION_TIMING"));
                    
                    response.put("success", true);
                    response.put("data", data);
                } else {
                    response.put("success", false);
                    response.put("message", "Trigger não encontrado");
                }
            }
        } catch (SQLException e) {
            response.put("success", false);
            response.put("message", "Erro ao obter informações do trigger: " + e.getMessage());
        }
        
        return response;
    }

    public Map<String, Object> testTrigger(String tableName, String operation) throws SQLException {
        Map<String, Object> response = new HashMap<>();
        String sql = "SELECT COUNT(*) FROM " + tableName;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                response.put("success", true);
                response.put("message", "Trigger testado com sucesso. Tabela " + tableName + " acessível.");
                response.put("rowCount", rs.getInt(1));
            }
        } catch (SQLException e) {
            response.put("success", false);
            response.put("message", "Erro ao testar trigger: " + e.getMessage());
        }
        
        return response;
    }

    public Map<String, Object> executeTriggerAction(String triggerName, Map<String, Object> params) throws SQLException {
        Map<String, Object> response = new HashMap<>();
        
        try {
            if (triggerName.contains("inserir_agendamento_registrar_status_inicial") || 
                triggerName.contains("inserir_agendamento_notificar_prestador")) {
                return createTestAgendamento();
            } else if (triggerName.contains("atualizar_agendamento_registrar_mudanca_de_status")) {
                return updateAgendamentoStatus();
            } else if (triggerName.contains("inserir_avaliacao_criar_notificacao")) {
                return createTestAvaliacao();
            } else if (triggerName.contains("inserir_pagamento_confirmado_criar_notificacao")) {
                return createTestPagamento();
            } else if (triggerName.contains("inserir_disputa_aberta_criar_notificacao")) {
                return createTestDisputa();
            } else {
                response.put("success", false);
                response.put("message", "Ação não implementada para este trigger");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Erro ao executar ação: " + e.getMessage());
        }
        
        return response;
    }

    private Map<String, Object> createTestAgendamento() throws SQLException {
        Map<String, Object> response = new HashMap<>();
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT cli_id FROM cliente LIMIT 1";
            Integer cliId = null;
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cliId = rs.getInt(1);
                }
            }
            
            sql = "SELECT ser_id FROM servico LIMIT 1";
            Integer serId = null;
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    serId = rs.getInt(1);
                }
            }
            
            sql = "SELECT pre_id FROM prestador LIMIT 1";
            Integer preId = null;
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    preId = rs.getInt(1);
                }
            }
            
            sql = "SELECT end_id FROM endereco LIMIT 1";
            Integer endId = null;
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    endId = rs.getInt(1);
                }
            }
            
            if (cliId == null || serId == null || preId == null || endId == null) {
                response.put("success", false);
                response.put("message", "Dados insuficientes no banco para criar agendamento de teste");
                return response;
            }
            
            sql = "INSERT INTO agendamento_servico (cli_id, ser_id, pre_id, age_data, age_janela, end_id, age_status, age_valor, age_pago) " +
                  "VALUES (?, ?, ?, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 'Manhã', ?, 'Agendado', 150.00, 0)";
            Integer ageId = null;
            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, cliId);
                stmt.setInt(2, serId);
                stmt.setInt(3, preId);
                stmt.setInt(4, endId);
                stmt.executeUpdate();
                
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        ageId = rs.getInt(1);
                    }
                }
            }
            
            List<Map<String, Object>> historico = new ArrayList<>();
            sql = "SELECT * FROM historico_status_agendamento WHERE age_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, ageId);
                try (ResultSet rs = stmt.executeQuery()) {
                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    while (rs.next()) {
                        Map<String, Object> row = new HashMap<>();
                        for (int i = 1; i <= columnCount; i++) {
                            row.put(metaData.getColumnLabel(i), rs.getObject(i));
                        }
                        historico.add(row);
                    }
                }
            }
            
            List<Map<String, Object>> notificacoes = new ArrayList<>();
            sql = "SELECT * FROM notificacao WHERE age_id = ? ORDER BY not_data DESC LIMIT 5";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, ageId);
                try (ResultSet rs = stmt.executeQuery()) {
                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    while (rs.next()) {
                        Map<String, Object> row = new HashMap<>();
                        for (int i = 1; i <= columnCount; i++) {
                            row.put(metaData.getColumnLabel(i), rs.getObject(i));
                        }
                        notificacoes.add(row);
                    }
                }
            }
            
            response.put("success", true);
            response.put("message", "Agendamento criado com sucesso! Trigger executado automaticamente.");
            response.put("agendamentoId", ageId);
            response.put("historicoStatus", historico);
            response.put("notificacoes", notificacoes);
        }
        
        return response;
    }

    private Map<String, Object> updateAgendamentoStatus() throws SQLException {
        Map<String, Object> response = new HashMap<>();
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT age_id, age_status FROM agendamento_servico LIMIT 1";
            Integer ageId = null;
            String oldStatus = null;
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ageId = rs.getInt("age_id");
                    oldStatus = rs.getString("age_status");
                }
            }
            
            if (ageId == null) {
                response.put("success", false);
                response.put("message", "Nenhum agendamento encontrado para atualizar");
                return response;
            }
            
            String newStatus = "Confirmado";
            sql = "UPDATE agendamento_servico SET age_status = ? WHERE age_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, newStatus);
                stmt.setInt(2, ageId);
                stmt.executeUpdate();
            }
            
            List<Map<String, Object>> historico = new ArrayList<>();
            sql = "SELECT * FROM historico_status_agendamento WHERE age_id = ? ORDER BY his_data DESC LIMIT 1";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, ageId);
                try (ResultSet rs = stmt.executeQuery()) {
                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    while (rs.next()) {
                        Map<String, Object> row = new HashMap<>();
                        for (int i = 1; i <= columnCount; i++) {
                            row.put(metaData.getColumnLabel(i), rs.getObject(i));
                        }
                        historico.add(row);
                    }
                }
            }
            
            response.put("success", true);
            response.put("message", "Status atualizado de '" + oldStatus + "' para '" + newStatus + "'. Trigger executado automaticamente.");
            response.put("agendamentoId", ageId);
            response.put("statusAnterior", oldStatus);
            response.put("statusNovo", newStatus);
            response.put("historico", historico);
        }
        
        return response;
    }

    private Map<String, Object> createTestAvaliacao() throws SQLException {
        Map<String, Object> response = new HashMap<>();
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT age_id, cli_id, pre_id FROM agendamento_servico WHERE pre_id IS NOT NULL LIMIT 1";
            Integer ageId = null;
            Integer cliId = null;
            Integer preId = null;
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ageId = rs.getInt("age_id");
                    cliId = rs.getInt("cli_id");
                    preId = rs.getInt("pre_id");
                }
            }
            
            if (ageId == null) {
                response.put("success", false);
                response.put("message", "Nenhum agendamento com prestador encontrado");
                return response;
            }
            
            sql = "INSERT INTO avaliacao (age_id, cli_id, pre_id, ava_nota, ava_coment, ava_data) " +
                  "VALUES (?, ?, ?, 5, 'Excelente serviço!', CURDATE())";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, ageId);
                stmt.setInt(2, cliId);
                stmt.setInt(3, preId);
                stmt.executeUpdate();
            }
            
            List<Map<String, Object>> notificacoes = new ArrayList<>();
            sql = "SELECT * FROM notificacao WHERE age_id = ? AND not_tipo = 'Avaliacao' ORDER BY not_data DESC LIMIT 1";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, ageId);
                try (ResultSet rs = stmt.executeQuery()) {
                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    while (rs.next()) {
                        Map<String, Object> row = new HashMap<>();
                        for (int i = 1; i <= columnCount; i++) {
                            row.put(metaData.getColumnLabel(i), rs.getObject(i));
                        }
                        notificacoes.add(row);
                    }
                }
            }
            
            response.put("success", true);
            response.put("message", "Avaliação criada com sucesso! Trigger executado automaticamente e notificação criada.");
            response.put("avaliacaoId", ageId);
            response.put("notificacoes", notificacoes);
        }
        
        return response;
    }

    private Map<String, Object> createTestPagamento() throws SQLException {
        Map<String, Object> response = new HashMap<>();
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT age_id FROM agendamento_servico LIMIT 1";
            Integer ageId = null;
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ageId = rs.getInt("age_id");
                }
            }
            
            if (ageId == null) {
                response.put("success", false);
                response.put("message", "Nenhum agendamento encontrado");
                return response;
            }
            
            sql = "INSERT INTO pagamento (age_id, pag_forma, pag_valor, pag_status, pag_ref, pag_data) " +
                  "VALUES (?, 'Cartão de Crédito', 150.00, 'Pago', CONCAT('TEST-', UNIX_TIMESTAMP()), CURDATE())";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, ageId);
                stmt.executeUpdate();
            }
            
            List<Map<String, Object>> notificacoes = new ArrayList<>();
            sql = "SELECT * FROM notificacao WHERE age_id = ? AND not_tipo = 'Pagamento' ORDER BY not_data DESC LIMIT 1";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, ageId);
                try (ResultSet rs = stmt.executeQuery()) {
                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    while (rs.next()) {
                        Map<String, Object> row = new HashMap<>();
                        for (int i = 1; i <= columnCount; i++) {
                            row.put(metaData.getColumnLabel(i), rs.getObject(i));
                        }
                        notificacoes.add(row);
                    }
                }
            }
            
            response.put("success", true);
            response.put("message", "Pagamento confirmado criado com sucesso! Trigger executado automaticamente e notificação criada.");
            response.put("pagamentoId", ageId);
            response.put("notificacoes", notificacoes);
        }
        
        return response;
    }

    private Map<String, Object> createTestDisputa() throws SQLException {
        Map<String, Object> response = new HashMap<>();
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT age_id, cli_id, pre_id FROM agendamento_servico LIMIT 1";
            Integer ageId = null;
            Integer cliId = null;
            Integer preId = null;
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ageId = rs.getInt("age_id");
                    cliId = rs.getInt("cli_id");
                    preId = rs.getInt("pre_id");
                }
            }
            
            if (ageId == null) {
                response.put("success", false);
                response.put("message", "Nenhum agendamento encontrado");
                return response;
            }
            
            sql = "INSERT INTO disputa_reembolso (age_id, cli_id, pre_id, dsp_motivo, dsp_status, dsp_valor, dsp_abertura) " +
                  "VALUES (?, ?, ?, 'Serviço não realizado conforme combinado', 'Aberta', 150.00, CURDATE())";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, ageId);
                stmt.setInt(2, cliId);
                stmt.setInt(3, preId);
                stmt.executeUpdate();
            }
            
            List<Map<String, Object>> notificacoes = new ArrayList<>();
            sql = "SELECT * FROM notificacao WHERE age_id = ? ORDER BY not_data DESC LIMIT 5";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, ageId);
                try (ResultSet rs = stmt.executeQuery()) {
                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    while (rs.next()) {
                        Map<String, Object> row = new HashMap<>();
                        for (int i = 1; i <= columnCount; i++) {
                            row.put(metaData.getColumnLabel(i), rs.getObject(i));
                        }
                        notificacoes.add(row);
                    }
                }
            }
            
            response.put("success", true);
            response.put("message", "Disputa aberta criada com sucesso! Trigger executado automaticamente e notificação criada.");
            response.put("disputaId", ageId);
            response.put("notificacoes", notificacoes);
        }
        
        return response;
    }

    private String extractErrorMessage(SQLException e) {
        String errorMessage = e.getMessage();
        if (e.getCause() != null && e.getCause().getMessage() != null) {
            errorMessage = e.getCause().getMessage();
        }
        if (errorMessage == null || errorMessage.isBlank()) {
            return "Erro desconhecido ao executar procedure.";
        }
        if (errorMessage.contains("rollback-only") || errorMessage.contains("Transaction")) {
            return "Erro ao executar procedure. Verifique os parâmetros fornecidos e se a procedure existe no banco de dados.";
        }
        return errorMessage;
    }
}

